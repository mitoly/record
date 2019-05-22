<template>
	
	<div class="maintainPage">

        <Form ref="modelForm" :model="modelForm" :rules="ruleInline" label-position="left" :label-width="90" inline>
        	<div>
	            <FormItem  prop="account" label="账户" >
	                <Input  v-model="modelForm.account" />
	            </FormItem>
	
	            <FormItem  prop="userName" label="用户名" >
	                <Input  v-model="modelForm.userName" />
	            </FormItem>
			</div>
			<div>
	            <FormItem  prop="phone" label="电话" >
	                <Input  v-model="modelForm.phone" />
	            </FormItem>
	
	            <FormItem  prop="email" label="邮箱" >
	                <Input  v-model="modelForm.email" />
	            </FormItem>
			</div>
			<div>
	            <FormItem  prop="gender" label="性别" >
	                <Select v-model="modelForm.gender">
				        <Option v-for="item in genderSelectList" :value="item.value" :key="item.value">{{ item.label }}</Option>
				    </Select>
	            </FormItem>
			</div>
        </Form>
    </div>
	
</template>

<script>
	import commonUtil from '@/libs/commonUtil';
	export default {
        data () {
            let validateAccount = (rule, value, callback) => {
                if(value.length>15){
                	commonUtil.showMessage("账户长度不能大于15！",3,this);
                    this.modelForm.account = "";
                }else{
                	this.modelForm.account = value.trim()
                }
                callback();
            };
            let validateUserName = (rule, value, callback) => {
                if(value.length>15){
                	commonUtil.showMessage("用户名长度不能大于15！",3,this);
                    this.modelForm.userName = "";
                }else{
                	this.modelForm.userName = value.trim()
                }
                callback();
            };
            let validatePhone = (rule, value, callback) => {
            	let mobile = /^\+?[1-9][0-9]*$/;
                if(!mobile.test(value)){
                	commonUtil.showMessage("号码格式不正确！",3,this);
                    this.modelForm.phone = "";
                }else{
                	this.modelForm.phone = value.trim()
                }
                callback();
            };
            let validateEmail = (rule, value, callback) => {
            	 var emailRE = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if(!emailRE.test(value)){
                	commonUtil.showMessage("邮箱格式不正确！",3,this);
                    this.modelForm.email = "";
                }else{
                	this.modelForm.email = value.trim()
                }
                callback();
            };
            return {
            	loading:true,
            	genderSelectList:[],
                modelForm:{
                	account:'', userName:'', phone:'', email:'', gender:'',optCounter:''
                },
                ruleInline:{
                    account:[
                        {required: true, message: '账户不能为空', trigger: 'blur' },
                        {validator: validateAccount, trigger: 'blur'}
                    ],
                    userName:[
                        {required: true, message: '用户名不能为空', trigger: 'blur' },
                        {validator: validateUserName, trigger: 'blur'}
                    ],
                    phone:[
                     	{required: true, message: '电话不能为空', trigger: 'blur' },
                        {validator: validatePhone, trigger: 'blur'}
                    ],
                    email:[
                     	{required: true, message: '邮箱不能为空', trigger: 'blur' },
                        {validator: validateEmail, trigger: 'blur'}
                    ],
                    gender:[
                     	{required: true, message: '性别不能为空', trigger: 'blur' },
                    ]
                }
            }
        },
        methods: {
            submit:function () {
                var me=this;
                this.$refs['modelForm'].validate((valid) => {
                    if (valid) {
                    	let url = '/user/add';
                    	if(me.modelForm && me.modelForm.id){
                    		url = '/user/edit';
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
        	this.genderSelectList = commonUtil.getDictEntry('TYPE_GENDER');
        },
        props: ['editData'],//父类往子类的传值
        watch: {//监视父类给子类传值的变化，例如第一个修改后，点击第二个修改
            editData(val) {
            	this.modelForm.id = val.id; 
            	this.modelForm.account = val.account; 
            	this.modelForm.userName = val.userName;
            	this.modelForm.phone = val.phone;
            	this.modelForm.email = val.email;
            	this.modelForm.gender = val.gender;
                this.modelForm.optCounter = val.optCounter;
            }
        }
    };
	
</script>

<style>
</style>