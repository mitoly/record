<template>
    <Transfer
            :titles="titles"
            :data="roleData"
            :target-keys="targetRoleData"
            :list-style="listStyle"
            @on-change="handleChange"
            filterable>
    </Transfer>
</template>

<script>
    import commonUtil from '@/libs/commonUtil';
    export default {
        name: "user_permission",
        data(){
            return {
                userId: '',
                titles: ["未添加角色", "已添加角色"],
                roleData: [],
                targetRoleData: [],
                listStyle: {
                    width: '250px',
                    height: '300px'
                }
            }
        },
        methods: {

        },
        methods: {
            initData: function (roleData, userId){
                let me = this;
                me.userId = userId;
                if (roleData) {
                    roleData.forEach(v=>{
                        me.roleData.push({key: v.ID.toString(), label: v.ROLE_NAME});
                        if (v.CHECKROLE) {
                            me.targetRoleData.push(v.CHECKROLE.toString())
                        }
                    });
                }
            },
            submit: function () {
                let me = this;
                commonUtil.doPost(me, "/user/savePermission", {"userId": me.userId, "targetRoleData": me.targetRoleData.join(",")})
                    .then(function (response) {
                        let result = response.data;
                        if(result.success){
                            commonUtil.showMessage('success',4, me)
                            me.$emit("permissionCallParent", '','MaintainOk');//调用父类保存成功方法
                        }else{
                            commonUtil.showMessage(result.message,3, me)
                            me.$emit("permissionCallParent", false,'ToggleLoading');//调用父类保存失败方法
                        }
                    })
            },
            clearData: function () {
                this.userId = '';
                this.roleData = [];
                this.targetRoleData = [];
            },
            handleChange: function (newTargetKeys) {
                this.targetRoleData = newTargetKeys;
            }
        },
        props: ['editData'],
        watch: {

        },

    }
</script>

<style scoped>

</style>