<template>

    <div class="maintainPage">

        <Form ref="modelForm" :model="modelForm" :rules="ruleInline" label-position="left" :label-width="90" inline>
            <div>
                <FormItem  prop="roleCode" label="角色代码" >
                    <Input  v-model="modelForm.roleCode" />
                </FormItem>

                <FormItem  prop="roleName" label="角色名称" >
                    <Input  v-model="modelForm.roleName" />
                </FormItem>
            </div>
        </Form>
    </div>

</template>

<script>
    import commonUtil from '@/libs/commonUtil';
    export default {
        data () {
            let validateRoleCode = (rule, value, callback) => {
                if(value.length>15){
                    commonUtil.showMessage("角色代码不能大于20！",3,this);
                    this.modelForm.roleCode = "";
                }else{
                    this.modelForm.roleCode = value.trim()
                }
                callback();
            };
            let validateRoleName = (rule, value, callback) => {
                if(value.length>15){
                    commonUtil.showMessage("用户名长度不能大于15！",3,this);
                    this.modelForm.roleName = "";
                }else{
                    this.modelForm.roleName= value.trim()
                }
                callback();
            };

            return {
                loading:true,
                genderSelectList:[],
                modelForm:{
                    roleCode:'', roleName:''
                },
                ruleInline:{
                    roleCode:[
                        {required: true, message: '角色代码不能为空', trigger: 'blur' },
                        {validator: validateRoleCode, trigger: 'blur'}
                    ],
                    roleName:[
                        {required: true, message: '角色名称不能为空', trigger: 'blur' },
                        {validator: validateRoleName, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            submit:function () {
                let me=this;
                this.$refs['modelForm'].validate((valid) => {
                    if (valid) {
                        let url = '/role/add';
                        if(me.modelForm && me.modelForm.id){
                            url = '/role/edit';
                        }
                        commonUtil.doPost(me, url, me.modelForm)
                            .then(function(response){
                                let result = response.data;
                                if(result.success){
                                    commonUtil.showMessage('success',4, me)
                                    me.$emit("callParent", '','MaintainOk');//调用父类保存成功方法
                                }else{
                                    commonUtil.showMessage(result.message,3, me)
                                    me.$emit("callParent", false,'ToggleLoading');//调用父类保存失败方法
                                }
                            })
                    } else {
                        me.$emit("callParent", false,'ToggleLoading');
                        me.$Message.error('表单验证失败!');
                    }
                });
            },clearForm:function () {
                commonUtil.clearFormItem(this.modelForm);//清除内容
            },clearValid:function () {
                this.$refs['modelForm'].resetFields();//清除验证
            }
        },
        mounted(){

        },
        props: ['editData'],//父类往子类的传值
        watch: {//监视父类给子类传值的变化，例如第一个修改后，点击第二个修改
            editData(val) {
                this.modelForm.id = val.id;
                this.modelForm.roleCode = val.roleCode;
                this.modelForm.roleName = val.roleName;
            }
        }
    };

</script>

<style scoped>

</style>