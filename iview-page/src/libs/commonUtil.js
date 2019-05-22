import util from './util.js';
import dict from './dict.js';
import Cookies from 'js-cookie';

/**
 * 像后台传输POST
 * @param {Object} page
 * @param {Object} url
 * @param {Object} param
 */
function doPost(page, url, param){
	let me = page;
	let qs = require('qs');
	return util.ajax.post(url, qs.stringify(param))
				.catch(function (response){
					showMessage("请重新登入", 3, me);
					//退出到登入界面
					me.$store.commit('logout', me);
			        me.$store.commit('clearOpenedSubmenu');
			        me.$router.push({
			            name: 'login'
			        });
        		});
}

/**
 * 像后台传输 GET
 * @param {Object} url
 * @param {Object} param
 */
function doGet(page, url, param){
    let me = page;
    let qs = require('qs');
	return util.ajax.get(url, {params : param})
		.catch(function (response){
            //退出到登入界面
            me.$store.commit('logout', me);
            me.$store.commit('clearOpenedSubmenu');
            me.$router.push({
                name: 'login'
            });
		});
}

function showMessage(msg, type, page) {
    if(type==2){
        page.$Message.warning(msg);
    }else if(type==3){
		page.$Message.error({
            content: msg,
            duration: 5, //持续时间
            closable: true  //关闭标志
        });
    }else if(type==4){
        page.$Message.success(msg);
    }else{
        page.$Message.info(msg);
    }
}

function formatDate(date, fmt) {
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
        }
    }
    return fmt;
}

/**
 * 列表查询
 * @param {Object} me
 * @param {Object} searchForm
 */
function loadData(me, searchForm){
	let condition = {};
	if(searchForm){
		condition = searchForm;
	}else if(me.searchForm){
		condition = me.searchForm;
	}else{
		return;
	}
	let param = {};
	
	for(let i in condition){
		if(condition[i]){
			param[i] = condition[i];
		}
	}
	if(!me.currentPage && !me.currentPage > 0){
		me.currentPage = 1;
	}
	param['currentPage'] = me.currentPage;
	param['pageSize'] = me.pageSize;
	doGet(me, me.listUrl, param).then(function(response){
		if(response.data.success){
			let data = response.data.data;
			me.columnDatas = data.list;
			me.total = data.total;
		}
	});
}

/**
 * 列表查询条件 复位
 * @param {Object} me
 * @param {Object} searchForm
 */
function refresh(me, searchForm){
	let condition = {};
	if(searchForm){
		condition = searchForm;
	}else if(me.searchForm){
		condition = me.searchForm;
	}else{
		return;
	}
	for(let i in condition){
		if(condition[i]){
			condition[i] = '';
		}
	}
}

function deleteData(me,url) {
    let ids= getSelectedIds(me.selections,false);
    if(ids){
        me.$Modal.confirm({
            title: '删除数据',
            content: '您确定删除所选数据？',
            onOk: () => {
            	doPost(me, url, {'ids':JSON.stringify(ids)}).then(function(response){
            		let result = response.data;
            		if(result.success){
                		loadData(me);
                		me.selections = [];
                		showMessage('删除数据成功',4, me);
                	}else{
                		showMessage('删除数据失败',3, me);
                	}
            	});
            }
        });


    }
}

function getEntryName(typeCode,entryCode){
	return dict.getValue(typeCode,entryCode);
}

function getDictEntry(typeCode){
	return dict.getDictEntry(typeCode);
}

function getSelectedRecord(selections,singleSelect){
    if(!selections || selections.length < 1){
        showMessage('请选择数据！');
        return false;
    }
    if(singleSelect && selections.length > 1){
        showMessage('只能选择一条数据!');
        return false;
    }
    return selections;
}
function getSelectedIds(selections,singleSelect){
    var selectedRecords = getSelectedRecord(selections,singleSelect);
    if(selectedRecords){
        var ids=new Array();
        for ( var i = 0; i < selectedRecords.length; i++) {
            ids.push(selectedRecords[i]['id']);
        }
        return ids;
    }
    return false;
}

function clearFormItem(modelForm){
	for(let i in modelForm){
		modelForm[i] = '';
	}
}

function init(result){
	let userVo = result.data.userVo;
    let permissionTable = result.data.permissionTable;
    let permissionButton = result.data.permissionButton;
	Cookies.set('curryUser', userVo);
	Cookies.set('userName', userVo.userName);
	Cookies.set('permissionTable', permissionTable);
    Cookies.set('permissionButton', permissionButton);
	let menusPermission = [];
	let roles = userVo.roles;		
	for(let i=0; i<roles.length; i++){
		 let menus = roles[i].menus;
		 for(let i=0; i<menus.length; i++){
		 	if(menus[i]){
		 		menusPermission.push(menus[i].permissionCode);
		 	}
		 }
		
	}
    Cookies.set('access', menusPermission.join(','));
	dict.init(result.data.dict);
}

export default{
	doPost,
	doGet,
	showMessage,
	formatDate,
	loadData,
	refresh,
	deleteData,
	getEntryName,
	getDictEntry,
	getSelectedRecord,
	getSelectedIds,
	clearFormItem,
	init,
};