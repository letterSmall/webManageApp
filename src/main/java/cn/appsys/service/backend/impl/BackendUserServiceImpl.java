package cn.appsys.service.backend.impl;

import cn.appsys.dao.BaseDao;
import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;

@Service
public class BackendUserServiceImpl implements BackendUserService {
	@Resource
	private BackendUserMapper mapper;
	
	@Override
	public BackendUser login(String userCode, String userPassword){
		// TODO Auto-generated method stub
		Connection connection = null;
		BackendUser backend_user = null;
		try {
//
			backend_user=mapper.getLoginUser(userCode,userPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return backend_user;

	}

}
