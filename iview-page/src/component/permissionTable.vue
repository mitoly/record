<template>
    <Table stripe border size="small" :columns="listColumn" :data="data" :style="tableSty" @on-selection-change="changeSelection"></Table>
</template>

<style></style>

<script>
    import Cookies from 'js-cookie';

    export default {
        data() {
            return {
                listColumn:[]
            }
        },
        mounted() {
            let me = this;
            let formTables = JSON.parse(Cookies.get('permissionTable'));
            let dataArray = new Array();
            if(formTables && formTables[this.formPermission]){
                let entrys = formTables[this.formPermission];
                let columns = this.columns;
                for(let i = 0; i < columns.length; i++){
                    let code = columns[i].permissionCode;

                    for(let y = 0; y < entrys.length; y++){
                        let codef = entrys[y];
                        if(codef==code){
                            dataArray.push(columns[i]);
                        }
                    }
                }
                me.listColumn = dataArray;
            }
        },
        methods:{
            changeSelection:function (selection) {
                this.$emit('s', selection);
            }
        },
        watch: {
        },
        props: ['columns', 'data', 'formPermission', 'tableSty']
    }

</script>