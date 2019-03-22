<template>
    <Row  type="flex" justify="space-between" class="code-row-bg">
        <Col span="4">
            <p>菜单列表<Button  class="role_btn" type="primary" size="small" shape="circle"  @click="savePermission()">保存</Button></p>
            <Tree :data="baseData" ref="menuTree" @on-select-change="selectChange" show-checkbox ></Tree>
        </Col>
        <Col span="4" v-show="isShow">
            <p>功能权限</p>
            <div style="border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
                <Checkbox
                        :indeterminate="indeterminateButton"
                        :value="checkAllButton"
                        @click.prevent.native="handleButtonCheckAll()">全选</Checkbox>
            </div>
            <CheckboxGroup v-model="checkAllGroupButton" v-for="buttonItem in buttonPermission" @on-change="checkAllButtonGroupChange()">
                <Checkbox :label="buttonItem.PERMISSION_NAME" :id="buttonItem.ID"></Checkbox>
            </CheckboxGroup>
        </Col>
        <Col span="4" v-show="isShow">
            <p>表单权限</p>
            <div style="border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
                <Checkbox
                        :indeterminate="indeterminateTable"
                        :value="checkAllTable"
                        @click.prevent.native="handleTableCheckAll()">全选</Checkbox>
            </div>
            <CheckboxGroup v-model="checkAllGroupTable" v-for="tableItem in tablePermission" @on-change="checkAllTableGroupChange()">
                <Checkbox :label="tableItem.PERMISSION_NAME" :id="tableItem.ID"></Checkbox>
            </CheckboxGroup>
        </Col>
    </Row>
</template>

<script>
    import commonUtil from '@/libs/commonUtil';

    export default {
        name: "role_permission",
        data () {
            return {
                baseData: [],
                menuData: [],
                roleId:'',
                indeterminateButton: false,
                indeterminateTable: false,
                checkAllButton: false,
                checkAllTable: false,
                checkAllGroupButton: [],
                checkAllGroupTable: [],
                isShow: false,
                buttonPermission: [],
                tablePermission: [],
            }
        },
        mounted() {

        },
        methods: {
            clear: function(){
                this.baseData = [];
                this.indeterminateButton= false;
                this.indeterminateTable= false;
                this.checkAllButton= false;
                this.checkAllTable= false;
                this.checkAllGroupButton= [];
                this.checkAllGroupTable= [];
                this.isShow= false;
                this.buttonPermission= [];
                this.tablePermission= [];
            },
            createMenuTree:function() {
                this.clear();
                if(this.menuData){
                    this.menuData.forEach(v=>{
                        let data = {'title': v.NAME,'expand': true,'children': [], 'code':v.CODE,'permissionCode':v.PERMISSION_CODE,'menuId':v.ID};
                        if(!v.PARENT_CODE){ // 父节点， 父节点没有PARENT_CODE
                            this.baseData.push(data);
                        }else{ // 子节点
                            if(v.CHECKROLE){
                                data.checked = true;
                            }
                            this.baseData[this.baseData.length-1].children.push(data);
                        }
                    });
                }
            },
            selectChange:function (selected) {
                let me = this;
                if (selected[0]) {
                    selected = selected[0];
                    commonUtil.doGet(this, "role/findPermissionCheckType", {'permissionTable':selected.code, 'roleId':me.roleId}).then(response => {
                        let result = response.data;
                        if(result.success){
                            me.buttonPermission = result.data.buttonPermission;
                            me.tablePermission = result.data.tablePermission;
                            me.buttonPermission.forEach(v=>{
                               if (v.CHECKROLE) {
                                   me.checkAllGroupButton.push(v.PERMISSION_NAME)
                               }
                            });
                            me.tablePermission.forEach(v=>{
                                if (v.CHECKROLE) {
                                    me.checkAllGroupTable.push(v.PERMISSION_NAME)
                                }
                            });

                            me.checkAllButtonGroupChange();
                            me.checkAllTableGroupChange();
                            me.isShow = true;
                        }
                    });
                } else {
                    me.isShow = false;
                }
            },
            handleButtonCheckAll () {
                debugger;
                let me = this;
                if (me.indeterminateButton) {
                    me.checkAllButton = false;
                } else {
                    me.checkAllButton = !me.checkAllButton;
                }
                me.indeterminateButton = false;

                me.checkAllGroupButton = [];
                if (me.checkAllButton) {
                    me.buttonPermission.forEach(v=>{
                        me.checkAllGroupButton.push(v.PERMISSION_NAME)
                    });
                }
            },
            checkAllButtonGroupChange() {
                if (this.checkAllGroupButton.length == this.buttonPermission.length) {
                    this.indeterminateButton = false;
                    this.checkAllButton = true;
                } else if (this.checkAllGroupButton.length > 0 ) {
                    this.indeterminateButton = true;
                    this.checkAllButton = false;
                } else {
                    this.indeterminateButton = false;
                    this.checkAllButton = false;
                }
            },
            handleTableCheckAll () {
                let me = this;
                if (me.indeterminateTable) {
                    me.checkAllTable = false;
                } else {
                    me.checkAllTable = !me.checkAllTable;
                }
                me.indeterminateTable = false;

                me.checkAllGroupTable = [];
                if (me.checkAllTable) {
                    me.tablePermission.forEach(v=>{
                        me.checkAllGroupTable.push(v.PERMISSION_NAME)
                    });
                }
            },
            checkAllTableGroupChange() {
                if (this.checkAllGroupTable.length == this.tablePermission.length) {
                    this.indeterminateTable = false;
                    this.checkAllTable = true;
                } else if (this.checkAllGroupTable.length > 0 ) {
                    this.indeterminateTable = true;
                    this.checkAllTable = false;
                } else {
                    this.indeterminateTable = false;
                    this.checkAllTable = false;
                }
            },
            savePermission:function () {
                let me = this;
                let selectMenu = me.$refs.menuTree.getSelectedNodes();
                let permissionCode = '';
                if (selectMenu[0]) {
                    permissionCode = selectMenu[0].code;
                }
                let saveMenuIdArr = [];
                let checkMenu = me.$refs.menuTree.getCheckedAndIndeterminateNodes();
                checkMenu.forEach(v=>{
                    saveMenuIdArr.push(v.menuId);
                });

                let saveButtonIdArr = [];
                me.buttonPermission.forEach(v=>{
                    if (me.checkAllGroupButton.indexOf(v.PERMISSION_NAME) != -1) {
                        saveButtonIdArr.push(v.ID);
                    }
                });
                let saveTableIdArr = [];
                me.tablePermission.forEach(v=>{
                    if (me.checkAllGroupTable.indexOf(v.PERMISSION_NAME) != -1) {
                        saveTableIdArr.push(v.ID);
                    }
                });
                commonUtil.doPost(me, "role/savePermission", {'menuIds':saveMenuIdArr.join(","),
                    'roleId':me.roleId, 'permissionCode':permissionCode, 'saveButtonIdStr':saveButtonIdArr.join(","),
                    'saveTableIdStr':saveTableIdArr.join(",")}).then(response=>{
                    let result = response.data;
                    if(result.success) {
                        commonUtil.showMessage("保存成功!", 4, me)
                    } else {
                        commonUtil.showMessage("异常！" + result.message, 3, me)
                    }
                });

            },

        },
        props: [],
        watch: {
        }
    }
</script>

<style scoped>
    .role_btn{
        width:60px;
        border-radius:5px;
        padding:0;
        margin-left:10px;
    }
</style>