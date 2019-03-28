package springboot.masterdata.user.controller;


import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.base.controller.BaseController;
import springboot.base.entity.JsonObjectResult;
import springboot.base.entity.QueryCriteria;
import springboot.masterdata.dict.service.TsDictService;
import springboot.masterdata.dict.vo.DictVo;
import springboot.masterdata.menu.entity.AbstractPermission;
import springboot.masterdata.menu.entity.TsPermissionButton;
import springboot.masterdata.menu.entity.TsPermissionTable;
import springboot.masterdata.role.entity.TsRole;
import springboot.masterdata.role.service.TsRoleService;
import springboot.masterdata.role.vo.RoleVo;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.service.TsUserService;
import springboot.masterdata.user.vo.UserVo;
import springboot.util.ActionUtil;
import springboot.util.ConstantUtils;
import springboot.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;


@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private TsUserService userService;
	@Autowired
	private TsDictService dictService;
	@Autowired
	private TsRoleService roleService;
	
	@GetMapping("/login")
	public JsonObjectResult login(HttpSession session, String account, String password){
		log.info("开始登入----账户：" + account + "----密码："+ password);
		if(StringUtils.isBlank(account))
			return ActionUtil.sendResult("账号不允许为空", false);
		if(StringUtils.isBlank(password))
			return ActionUtil.sendResult("密码不允许为空", false);

		UserVo userVo = userService.findUserByAccount(account);
		if(null == userVo)
			return ActionUtil.sendResult("账号错误", false);
		else if(!ConstantUtils.NO.equals(userVo.getIsEnabled()))
			return ActionUtil.sendResult("账号已被锁定", false);
		else if(password.equals(userVo.getPassword())){

			Map<String, Set> tableMap = new HashMap<String, Set>();
			Map<String, Set> buttonMap = new HashMap<String, Set>();
			for (int i=0,size=userVo.getRoles().size(); i<size; i++) {
				RoleVo roleVo = userVo.getRoles().get(i);
				computePermission(tableMap, roleVo.getPermissionTables());
				computePermission(buttonMap, roleVo.getPermissionButtons());
			}

			session.setAttribute(ConstantUtils.CURRENT_USER, userVo);
			List<DictVo> dictList = dictService.getDictList();

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("userVo", userVo); //当前登入信息
			result.put("dict", dictList); //字典信息
			result.put("permissionTable", tableMap);
			result.put("permissionButton", buttonMap);
			userVo.setLastLoginDate(DateUtils.getCurrentDate());
			userService.update(userVo);
			return ActionUtil.sendResult(result);
		}else
			return ActionUtil.sendResult("密码错误", false);
	}

	@GetMapping("/list")
	public JsonObjectResult list(QueryCriteria queryCriteria, TsUser user){
		PageInfo<TsUser> pageInfo = userService.list(queryCriteria, user);
		return ActionUtil.sendResult(pageInfo);
	}

	@PostMapping("/add")
	public JsonObjectResult add(HttpServletRequest request, HttpServletResponse response, TsUser user){
		UserVo isValid = userService.findUserByAccount(user.getAccount());
		if(null != isValid){ //账户存在
			return ActionUtil.sendResult("账户已存在！", false);
		}
		userService.addUser(request, response, user);
		return ActionUtil.sendResult();
	}

	@PostMapping("/edit")
	public JsonObjectResult edit(HttpServletRequest request, HttpServletResponse response, TsUser user){
		UserVo isValid = userService.findUserByAccount(user.getAccount());
		UserVo before = userService.findUserById(user.getId());
		if(!isValid.getId().equals(before.getId())){
			return ActionUtil.sendResult("账户已存在！", false);
		}
		BeanUtils.copyProperties(before, user, "account", "userName", "phone", "email", "gender");
		userService.editUser(request, response, user);
		return ActionUtil.sendResult();
	}

	/**
	 * 逻辑删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/remove")
	public JsonObjectResult remove(HttpServletRequest request, HttpServletResponse response, String ids){
		ids = ids.substring(1, ids.length()-1);
		UserVo userVo = super.getCurrentUser(request, response);
		if(StringUtils.isNotBlank(ids)){
			userService.remove(ids, userVo);
		}
		return ActionUtil.sendResult();
	}

	@GetMapping("/findById")
	public JsonObjectResult findUserById(Integer id){
		UserVo userVo = userService.findUserById(id);
		return ActionUtil.sendResult(userVo);
	}

	/**
	 * 获取用户角色 权限设置状态
	 * @param userId
	 * @return
	 */
	@GetMapping("/findRoleCheckType")
	public JsonObjectResult findRoleCheckType(Integer userId){
		List<Map<String, Object>> roleList = roleService.findRoleCheckType(userId);
		return ActionUtil.sendResult(roleList);
	}

	/**
	 * 保存用户与角色的权限
	 * @return
	 */
	@PostMapping("/savePermission")
	public JsonObjectResult savePermission(Integer userId, String targetRoleData) {
		String[] strArr = targetRoleData.split(",");
		Integer[] saveRoleArr = null;
		if (StringUtils.isNotBlank(strArr[0])) {
			saveRoleArr = new Integer[strArr.length];
			for (int i = 0; i < strArr.length; i++)
				saveRoleArr[i] = Integer.valueOf(strArr[i]);
		}
		userService.savePermission(userId, saveRoleArr);
		return ActionUtil.sendResult();
	}

	/**
	 * 登入处理权限
	 * @param tableMap
	 * @param permissionTables
	 */
	private void computePermission(Map<String, Set> tableMap, List<AbstractPermission> permissionTables) {
		for (AbstractPermission table : permissionTables) {
			tableMap.compute(table.getPermissionTable(), (key, set) -> {
				if (null == set)
					set = new HashSet();
				set.add(table.getPermissionCode());
				return set;
			});
		}
	}










	/*--------------------------------------------------------------*/

	@GetMapping("/test")
	public List<RoleVo> test(HttpSession session, Integer userId){
		List<RoleVo> roles = userService.findRoleByUserId(userId);
		UserVo user = (UserVo) session.getAttribute(ConstantUtils.CURRENT_USER);
		return roles;
	}
	
	@GetMapping("/test1")
	public UserVo test(String account){
		UserVo userVo = userService.findUserByAccount(account);
		return userVo;
	}

	public static void main(String[] str){
		List<String> collected = new ArrayList<>();
		collected.add("alpha");
		collected.add("beta");
		collected = collected.stream().map(string -> string.toUpperCase()).collect(Collectors.toList());
		System.out.println(collected);

		Map<String, String> map = new HashMap<String, String>();
		map.putIfAbsent("a","a");
		System.out.println(map.get("a"));
		map.putIfAbsent("a","aa");
		System.out.println(map.get("a"));
		map.computeIfAbsent("s", s -> "sss");
		System.out.println(map.get("s"));

		map.computeIfPresent("s", (s,b) -> {
			return "bbb";
		});
		System.out.println(map.get("s"));
		System.out.println(map.compute("c", (a,b) -> "c"));
		System.out.println(map.get("c"));
		System.out.println(map.compute("c", (a,b) -> "cc"));
		System.out.println(map.get("c"));
//		UserController u = new UserController();
//		u.testtt("你好大家好");
	}

	private void testtt(String str){
		Runnable r = () -> {
			System.out.println("");
		};
		Thread thread = new Thread(() -> {
			System.out.println(str);
			try {
				Thread.sleep(5000);
				System.out.println(str + "5000");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();

		Thread t1 = new Thread(){
			@Override
			public void run() {
				System.out.println(str+ "1");
				try {
					Thread.sleep(5000);
					System.out.println(str + "1000");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t1.start();
	}
}
