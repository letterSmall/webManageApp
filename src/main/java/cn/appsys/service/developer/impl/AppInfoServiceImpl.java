package cn.appsys.service.developer.impl;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.developer.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper mapper;
	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public boolean add(AppInfo appInfo)  {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.add(appInfo) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean modify(AppInfo appInfo) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.modify(appInfo) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean deleteAppInfoById(Integer delId)  {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.deleteAppInfoById(delId) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public AppInfo getAppInfo(Integer id, String APKName) {
		// TODO Auto-generated method stub
		return mapper.getAppInfo(id,APKName);
	}

	@Override
	public List<AppInfo> getAppInfoList(String querySoftwareName,
                                        Integer queryStatus, Integer queryCategoryLevel1,
                                        Integer queryCategoryLevel2, Integer queryCategoryLevel3,
                                        Integer queryFlatformId, Integer devId, Integer currentPageNo,
                                        Integer pageSize)  {
		// TODO Auto-generated method stub
		return mapper.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
                               Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                               Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId)
			 {
		// TODO Auto-generated method stub
		return mapper.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,devId);
	}

	@Override
	public boolean deleteAppLogo(Integer id)  {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.deleteAppLogo(id) > 0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 业务：根据appId删除APP信息
	 * 1、通过appId，查询app_verion表中是否有数据
	 * 2、若版本表中有该app应用对应的版本信息，则进行级联删除，先删版本信息（app_version），后删app基本信息（app_info）
	 * 3、若版本表中无该app应用对应的版本信息，则直接删除app基本信息（app_info）。
	 * 注意：事务控制，上传文件的删除
	 */
	@Override
	public boolean appsysdeleteAppById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		int versionCount = appVersionMapper.getVersionCountByAppId(id);
		List<AppVersion> appVersionList = null;
		if(versionCount > 0){//1 先删版本信息
			//<1> 删除上传的apk文件
			appVersionList = appVersionMapper.getAppVersionList(id);
			for(AppVersion appVersion:appVersionList){
				if(appVersion.getApkLocPath() != null && !appVersion.getApkLocPath().equals("")){
					File file = new File(appVersion.getApkLocPath());
					if(file.exists()){
						if(!file.delete())
							throw new Exception();
					}
				}
			}			
			//<2> 删除app_version表数据
			appVersionMapper.deleteVersionByAppId(id);
		}
		//2 再删app基础信息
		//<1> 删除上传的logo图片
		AppInfo appInfo = mapper.getAppInfo(id, null);
		if(appInfo.getLogoLocPath() != null && !appInfo.getLogoLocPath().equals("")){
			File file = new File(appInfo.getLogoLocPath());
			if(file.exists()){
				if(!file.delete())
					throw new Exception();
			}
		}
		//<2> 删除app_info表数据
		if(mapper.deleteAppInfoById(id) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfoObj) throws Exception {
		/*
		 * 上架：
		 * 2状态为审核通过
		 * 5为已下架
		 * 4为已上架
		 */
		
		Integer operator = appInfoObj.getModifyBy();
		if(operator < 0 || appInfoObj.getId() < 0 ){
			throw new Exception();
		}
		
		//get appinfo by appid
		AppInfo appInfo = mapper.getAppInfo(appInfoObj.getId(), null);
		if(null == appInfo){
			return false;
		}else{
			switch (appInfo.getStatus()) {
				case 2: //当状态为审核通过时，可以进行上架操作
					onSale(appInfo,operator,4,2);
					break;
				case 5://当状态为下架时，可以进行上架操作
					onSale(appInfo,operator,4,2);
					break;
				case 4://当状态为上架时，可以进行下架操作
					offSale(appInfo,operator,5);
					break;

			default:
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * on Sale
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @param versionStatus
	 * @throws Exception
	 */
	private void onSale(AppInfo appInfo, Integer operator, Integer appInfStatus, Integer versionStatus)  {
		try {
			offSale(appInfo,operator,appInfStatus);
			setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * offSale
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @return
	 * @throws Exception
	 */
	private boolean offSale(AppInfo appInfo, Integer operator, Integer appInfStatus) throws Exception {
		AppInfo _appInfo = new AppInfo();
		_appInfo.setId(appInfo.getId());
		_appInfo.setStatus(appInfStatus);
		_appInfo.setModifyBy(operator);
		_appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
		mapper.modify(_appInfo);
		return true;
	}
	
	/**
	 * set sale method to on or off
	 * @param appInfo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private boolean setSaleSwitchToAppVersion(AppInfo appInfo, Integer operator, Integer saleStatus) throws Exception {
		AppVersion appVersion = new AppVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date(System.currentTimeMillis()));
		appVersionMapper.modify(appVersion);
		return false;
	}
	
}
