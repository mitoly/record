<template>
    <Button type="primary" :icon="icon" v-show="isShow">{{buttonText}}</Button>
</template>

<script>
    import Cookies from 'js-cookie';

    export default {
        data(){
            return {
                isShow: false
            }
        },
        mounted(){
            let flag = false;
            let buttonObj = JSON.parse(Cookies.get('permissionButton'));
            if(buttonObj && buttonObj[this.formPermission]){
                let entrys = buttonObj[this.formPermission];
                for ( let i = 0; i < entrys.length; i++) {
                    if(entrys[i] == this.permissionCode){
                        flag = true;
                        break;
                    }
                }
                this.isShow = flag;
            }
        },
        props: ['icon', 'formPermission', 'permissionCode','buttonText']
    }
</script>

<style scoped>

</style>