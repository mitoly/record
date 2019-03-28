<style scoped>
	@import "../../../styles/pageCommon.less";
</style>

<template>
    <div class="listPage">
        <div class="search" ref="search" v-on:keyup.enter="handleSearch"  >
           <Form :ref="searchForm" :model="searchForm" label-position="left" inline >
				<FormItem  prop="roleCode" label="角色代码" :label-width="60" style="margin-left: 10px;">
                   <Input  v-model="searchForm.roleCode" />
               </FormItem>
               <FormItem  prop="roleName" label="角色名称" :label-width="60" style="margin-left: 30px;">
                   <Input   v-model="searchForm.roleName" />
               </FormItem>
                <div style="text-align: right;">
                	<FormItem>
		                <Button type="primary" icon="ios-search" @click="handleSearch" style="margin-right: 15px;margin-bottom: 5px;">搜索</Button>
		                <Button type="primary" icon="ios-refresh" @click="handleRefresh" style="margin-right: 20px;margin-bottom: 5px;">重置</Button>
	                </FormItem>
	            </div>
           </Form>
        </div>
        
         <div class="list">
             <div class="operations">
             	<!--<Button type="primary" @click="showModel = true" :disabled="selections.length<=-1" icon="plus">{{$t('btn.add')}}</Button>-->
             	<!--<Button type="primary" @click="edit" :disabled="selections.length!=1" icon="edit">{{$t('btn.edit')}}</Button>-->
             	<!--<Button type="primary" @click="remove" :disabled="selections.length<1" icon="close-circled">{{$t('btn.del')}}</Button>-->
                <span @click="showModel = true"><permissionButton :disabled="selections.length<=-1" icon="ios-add" formPermission="ROLE" permissionCode="ROLE_ADD" :buttonText="$t('btn.add')"></permissionButton></span>
                <span @click="edit"><permissionButton :disabled="selections.length!=1" icon="ios-create" formPermission="ROLE" permissionCode="ROLE_EDIT" :buttonText="$t('btn.edit')"></permissionButton></span>
                <span @click="remove"><permissionButton :disabled="selections.length<1" icon="ios-trash" formPermission="ROLE" permissionCode="ROLE_REMOVE" :buttonText="$t('btn.del')"></permissionButton></span>
                <span @click="editPermission"><permissionButton :disabled="selections.length!=1" icon="ios-key" formPermission="ROLE" permissionCode="ROLE_PERMISSION" :buttonText="$t('btn.editPermission')"></permissionButton></span>
             </div>
			
			<div style="overflow:scroll;" class="paging">
				<!--<Table stripe border size="small" :columns="columns" :data="columnDatas" @on-selection-change="selectChange"></Table>-->
                <permissionTable :columns="columns" :data="columnDatas" formPermission="ROLE"  v-on:s="(x) => selections=x"></permissionTable>
			</div>
             <div class="paging">
             	<Page :total="total" :current="currentPage" :page-size="pageSize" @on-change="pageOnchange" :page-size-opts="pageSizeOpts" @on-page-size-change="pageSizeOnchange" show-sizer show-elevator show-total placement="top"></Page>
        	 </div>
         </div>
		<div>
            <Modal v-model="showModel" :loading="loading" @on-ok="modelOk" @on-cancel="modelClose" width="600px" :title='title'>
                <roleModel ref="roleModel" :editData="this.editData" v-on:callParent="callParent"/>
            </Modal>
        </div>
        <div>
            <Modal v-model="showPermissionModel" :loading="loading" @on-ok="permissionOk" width="1200px" :title='title'>
                <permissionModel ref="permissionModel"/>
            </Modal>
        </div>
    </div>
</template>

<script>
	
import commonUtil from '@/libs/commonUtil';
import roleModel from "./role_model.vue";
import permissionModel from "./role_permission.vue";

export default {
    name: 'rolw_list',
    data () {
            return {
            	title:'',
                loading:true,
                listUrl:'/role/list',
                selections:[],
                pageSize:20,
            	pageSizeOpts:[20, 40, 80, 100],
        		total:1,
        		currentPage:1,
                editData:false,
                menuData:false,
                roleId:false,
                showModel:false,
                showPermissionModel:false,
                listDatas: [],
                searchForm: {
                    roleCode:'',
                    roleName:'',
                },
                columns: [
                	{
                        type: 'selection',
                        width: 60,
                        permissionCode:'ROLE_CODE',
                        align: 'center'
                    },
                    {
                        title: '角色代码',
                        key: 'roleCode',
                        permissionCode:'ROLE_CODE',
                        sortable: true
                    },
                    {
                        title: '角色名称',
                        key: 'roleName',
                        permissionCode:'ROLE_NAME',
                        sortable: true
                    },
                ],
                columnDatas: []
            }
        },
    methods: {
        getData () {
        	
        },
        pageSizeOnchange:function(val){
    		this.currentPage = 1;
    		this.pageSize = val;
    		commonUtil.loadData(this);
    		this.selections = [];
    	},
    	pageOnchange:function(val){
    		this.currentPage = val;
    		commonUtil.loadData(this);
    		this.selections = [];
   		},
        selectChange: function(selection){
        	this.selections = selection;
        },
        handleSearch:function(){
        	commonUtil.loadData(this);
        },
		handleRefresh:function(){
			commonUtil.handleRefresh(this);
		},
        edit:function(){
            let me=this;
         	this.title='权限设置';
        	let ids= commonUtil.getSelectedIds(this.selections,true);
        	if(ids){
            	commonUtil.doGet(this, "/role/findById",{'roleId':ids[0]}).then(function(response){
            		let result = response.data;
            		if(result.success){
            			me.editData = result.data;
            			me.showModel = true;
            		}
            	});
         	}
        },
        modelOk: function(){
        	this.$refs.roleModel.submit();//点击按钮时调用子页面的提交方法
        },
        modelClose:function(){
        	this.showModel=false;
        	this.$refs.roleModel.clearForm();
        	this.$refs.roleModel.clearValid();
        },
        callParent:function (data,type) {//子页面调用父页面的方法
            if (type=='ToggleLoading'){
                this.loading=data;
                this.$nextTick(() => {
                    this.loading = true;
                });

            }else if(type=='MaintainOk'){
               this.showModel=false;
                commonUtil.loadData(this);
            }
        },
        remove:function(){
        	commonUtil.deleteData(this, '/role/remove');
        },
        editPermission: function () {
            let me=this;
            this.title='权限设置';
            let ids= commonUtil.getSelectedIds(this.selections,true);
            if(ids){
                commonUtil.doGet(this, "/role/findMenusCheckType",{'onlyParent':false,'roleId':ids[0],'parentCode':''}).then(function(response){
                    let result = response.data;
                    if(result.success){
                        me.$refs.permissionModel.roleId = ids[0];
                        me.$refs.permissionModel.menuData = result.data;
                        me.$refs.permissionModel.createMenuTree();
                        me.showPermissionModel = true;
                    }
                });
            }
        },
        permissionOk: function(){
            this.showPermissionModel = false;
        },
    },
    mounted() {
    	commonUtil.loadData(this);
    },
    created () {
        this.getData();
    },
    components: {'roleModel': roleModel, 'permissionModel':permissionModel}
};
</script>
