import Cookies from 'js-cookie';

function init(entrys) {
	// 初始化字典表
	let dictEntrys = {};
	for(var i=0; i<entrys.length; i++){
		let dictEntryList = entrys[i].dictEntryList;
		let typeCode = entrys[i].typeCode;
		if(!dictEntrys[typeCode]){
			dictEntrys[typeCode] = {};
		}
		for(var j=0; j<dictEntryList.length; j++){
			let entryCode = dictEntryList[j].entryCode;
			dictEntrys[typeCode][entryCode] = dictEntryList[j];
		}
	}
	Cookies.set('dict', dictEntrys);
}

/**
 * 获取字典表中的entryName 国际化部分还没做
 * @param {Object} typeCode 
 * @param {Object} entryCode
 */
function getValue(typeCode,entryCode){
	let dict = Cookies.get('dict');
	let dictEntrys = JSON.parse(dict);
    if(dictEntrys && dictEntrys[typeCode]){
        return dictEntrys[typeCode][entryCode]?dictEntrys[typeCode][entryCode].entryName:entryCode;
    }
}

function getDictEntry(typeCode){
	let dict = Cookies.get('dict');
	let dictEntrys = JSON.parse(dict);
	let entryArr = [];
	if(dictEntrys && dictEntrys[typeCode]){
		let dictType = dictEntrys[typeCode];
		for(let i in dictType){
			entryArr.push({
				value: i,
				label: dictType[i].entryName
			});
		}
    }
	return entryArr;
}

export default
{
    init,
    getValue,
    getDictEntry
};
