package cn.appsys.dao.devuser;

import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

public interface DevUserMapper {
	/**
	 * 通过userCode获取User
	 * @return
	 * @throws Exception
	 */
	DevUser getLoginUser(@Param("devCode") String devCode)throws Exception;
}
