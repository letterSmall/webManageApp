package cn.appsys.service.developer;

import cn.appsys.pojo.AppInfo;

import java.util.List;

public interface AppInfoService {
	
	/**
	 * 新增app
	 * @param appInfo
	 * @return
	 * @throws Exception
	 */
	boolean add(AppInfo appInfo);
	/**
	 * 修改app信息
	 * @param appInfo
	 * @return
	 * @throws Exception
	 */
	boolean modify(AppInfo appInfo);
	
	/**
	 * 根据appId删除app应用
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	boolean deleteAppInfoById(Integer delId);
	
	/**
	 * 根据条件查询出app列表
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List<AppInfo> getAppInfoList(String querySoftwareName, Integer queryStatus,
                                        Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                                        Integer queryCategoryLevel3, Integer queryFlatformId,
                                        Integer devId, Integer currentPageNo, Integer pageSize);

	/**
	 * 根据条件查询appInfo表记录数
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @return
	 * @throws Exception
	 */
	int getAppInfoCount(String querySoftwareName, Integer queryStatus,
                               Integer queryCategoryLevel1, Integer queryCategoryLevel2,
                               Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId);
	/**
	 * 根据id、apkName查找appInfo
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AppInfo getAppInfo(Integer id, String APKName);
	
	/**
	 * 删除logo图片
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteAppLogo(Integer id);
	
	/**
	 * 通过appId删除app应用(app_info、app_version)
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean appsysdeleteAppById(Integer id) throws Exception;
	
	
	/**
	 * update Sale Status By AppId and Operator
	 * @param
	 * @return
	 * @throws Exception
	 */
	boolean appsysUpdateSaleStatusByAppId(AppInfo appInfo) throws Exception;
}
