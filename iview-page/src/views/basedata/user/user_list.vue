<style scoped>
	@import "../../../styles/pageCommon.less";
</style>

<template>
    <div class="listPage">
        <div class="search" ref="search" v-on:keyup.enter="handleSearch"  >
           <Form :ref="searchForm" :model="searchForm" label-position="left" inline >
				<FormItem  prop="account" label="账号" :label-width="45" style="margin-left: 10px;">
                   <Input  v-model="searchForm.account" />
               </FormItem>
               <FormItem  prop="userName" label="用户名" :label-width="60" style="margin-left: 30px;">
                   <Input   v-model="searchForm.userName" />
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
                 <span @click="showModel = true"><permissionButton :disabled="selections.length<=-1" icon="ios-add" formPermission="USER" permissionCode="USER_ADD" :buttonText="$t('btn.add')"></permissionButton></span>
                 <span @click="edit"><permissionButton :disabled="selections.length!=1" icon="ios-create" formPermission="USER" permissionCode="USER_EDIT" :buttonText="$t('btn.edit')"></permissionButton></span>
                 <span @click="remove"><permissionButton :disabled="selections.length<1" icon="ios-trash" formPermission="USER" permissionCode="USER_REMOVE" :buttonText="$t('btn.del')"></permissionButton></span>
                 <span @click="editPermission"><permissionButton :disabled="selections.length!=1" icon="ios-key" formPermission="USER" permissionCode="USER_PERMISSION" :buttonText="$t('btn.editPermission')"></permissionButton></span>
             </div>
			
			<div style="overflow:scroll;" class="paging">
                <permissionTable :columns="columns" :data="columnDatas" formPermission="USER"  v-on:s="(x) => selections=x"></permissionTable>
			</div>
             <div class="paging">
             	<Page :total="total" :current="currentPage" :page-size="pageSize" @on-change="pageOnchange" :page-size-opts="pageSizeOpts" @on-page-size-change="pageSizeOnchange" show-sizer show-elevator show-total placement="top"></Page>
        	 </div>
         </div>
		<div>
            <Modal v-model="showModel" :loading="loading" @on-ok="modelOk" @on-cancel="modelClose" width="600px" :title='title'>
                <UserModel ref="userModel" :editData="this.editData" v-on:callParent="callParent"/>
            </Modal>
            <Modal v-model="showPermissionModel" :loading="loading" @on-ok="permissionModelOk" @on-cancel="permissionModelClose" width="800px" :title='title'>
                <PermissionModel ref="permissionModel" :editData="this.editData" v-on:permissionCallParent="permissionCallParent"/>
            </Modal>
        </div>
    </div>
</template>

<script>
	
import commonUtil from '@/libs/commonUtil';
import UserModel from "./user_model.vue";
import PermissionModel from "./user_permission.vue";

export default {
    name: 'user_list',
    data () {
            return {
            	title:'',
                loading:true,
                listUrl:'/user/list',
                selections:[],
                pageSize:20,
            	pageSizeOpts:[20, 40, 80, 100],
        		total:1,
        		currentPage:1,
                editData:false,
                showModel:false,
                showPermissionModel:false,
                listDatas: [],
                searchForm: {
                    account:'',
                    userName:'',
                },
                columns: [
                	{
                        type: 'selection',
                        width: 60,
                        permissionCode:'ACCOUNT',
                        align: 'center'
                    },
                    {
                        title: '账户',
                        key: 'account',
                        permissionCode:'ACCOUNT',
                        sortable: true
                    },
                    {
                        title: '密码',
                        key: 'password',
                        permissionCode:'PASSWORD',
                        sortable: true
                    },
                    {
                        title: '用户名',
                        key: 'userName',
                        permissionCode:'USERNAME',
                        sortable: true
                    },
                    {
                        title: '电话',
                        key: 'phone',
                        permissionCode:'PHONE',
                        sortable: true
                    },
                    {
                        title: 'Email',
                        key: 'email',
                        permissionCode:'EMAIL',
                        sortable: true
                    },
                    {
                        title: '性别',
                        key: 'gender',
                        permissionCode:'GENDER',
                        render:function (h,params) {
                            return h('div',{},commonUtil.getEntryName('TYPE_GENDER',params.row.gender));
                        },
                        sortable: true
                    },
                    {
                        title: '锁定',
                        key: 'isEnabled',
                        permissionCode:'IS_ENABLED',
                        render:function (h,params) {
                            return h('div',{},commonUtil.getEntryName('YES_OR_NO',params.row.isEnabled));
                        },
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
            commonUtil.refresh(this);
        },
        edit:function(){
            let me=this;
         	this.title='编辑';
        	let ids= commonUtil.getSelectedIds(this.selections,true);
        	if(ids){
            	commonUtil.doGet(this, "/user/findById",{'id':ids[0]}).then(function(response){
            		let result = response.data;
            		if(result.success){
            			me.editData = result.data;
            			me.showModel = true;
            		}
            	});
         	}
        },
        modelOk: function(){
        	this.$refs.userModel.submit();//点击按钮时调用子页面的提交方法
        },
        modelClose:function(){
        	this.showModel=false;
        	this.$refs.userModel.clearForm();
        	this.$refs.userModel.clearValid();
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
        	commonUtil.deleteData(this, '/user/remove');
        },
        editPermission: function () {
            let me=this;
            this.title='用户权限';
            let ids= commonUtil.getSelectedIds(this.selections,true);
            if(ids){
                commonUtil.doGet(this, "/user/findRoleCheckType",{'userId':ids[0]}).then(function(response){
                    let result = response.data;
                    if(result.success){
                        me.$refs.permissionModel.initData(result.data, ids[0]);
                        me.showPermissionModel = true;
                    }
                });
            }
        },
        permissionModelOk: function(){
            this.$refs.permissionModel.submit();
        },
        permissionModelClose:function(){
            this.showPermissionModel=false;
            this.$refs.permissionModel.clearData();
        },
        permissionCallParent:function (data,type) {//子页面调用父页面的方法
            if (type=='ToggleLoading'){
                this.loading=data;
                this.$nextTick(() => {
                    this.loading = true;
                });

            }else if(type=='MaintainOk'){
                this.permissionModelClose();
            }
        },
    },
    mounted() {
    	commonUtil.loadData(this);
    },
    created () {
        this.getData();
    },
    components: { 'UserModel':UserModel, 'PermissionModel':PermissionModel}//自定义组件
};
</script>
