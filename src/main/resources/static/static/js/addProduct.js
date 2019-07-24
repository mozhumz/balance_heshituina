 $(function(){

  var mainV=new Vue({
   el: '#main',
   data:{
 	 tabPosition: 'left',
 	 //表格数据
 	 customerList: [],
 	 //客户列表
 	 customerList_f:true,
 	 //添加客户
 	 addCustomer_f:false,
 	 serviceSet_f:false,
 	 welcomeUser:'',
 	 keyword:'',
 	 customer:{name:'',phone:'',money:'',customerNo:'',gender:null,birthDate:null,remark:'',password:null,password2:null},
 	 balanceLog:{customerId:null,money:null,productIds:[],type:null,remark:null,doName:null},
 	 productList:[],
     addProd:{name:null},
     deleteProd:{id:null},
     updateProd:{id:null,name:null}
   },
//   filters:{
//
//   },
   methods:{
        changeMenu(menuType){
            switch(menuType){
                case 1:
                //客户列表
                 	 this.customerList_f=true;
                 	 this.addCustomer_f=false;
                 	 this.serviceSet_f=false;
                    this.getCustomerList(1);
                    break;
                case 2:
                //添加客户
                    this.customerList_f=false;
                    this.addCustomer_f=true;
                    this.serviceSet_f=false;
                    break;
                case 3:
                //消费项目设置
                    this.customerList_f=false;
                    this.addCustomer_f=false;
                    this.serviceSet_f=true;
                    this.getProductList(1);
                    break;
                default:
                    break;
            }
        },
        formatDate(time) {
                if(!time){
                            return null
                        }
               var date = new Date(time);
               return formatDate(date, 'yyyy-MM-dd hh:mm');
             },
        //提示消息
        open(res) {
                var msg=res.message;
                var type='error';
                if(res.status){
                    type='success';
                    msg=ok;
                }
                this.$message({
                    message: msg,
                    type: type
                });
             },
        handleEdit(index, row) {
               console.log(index, row);
               console.log(row.status);
               row.status=true;
             },
        handleDelete(index, row) {
           console.log(index, row);
               row.status=false;
         },
         //客户列表
         getCustomerList(type){
            if(type){
                this.keyword='';
            }
            var param={};
            if(this.keyword){
                param={keyword:this.keyword};
            }
            var res=ajax('POST',customersUrl,param);

            if(!res.status){
                this.open(res);
                return false;
            }
            for(let i of res.data.records){
                    i.createDate=this.formatDate(i.createDate);
                    i.gender=getGender(i.gender);
                    i.birthDate=this.formatDate(i.birthDate);
                }
            this.customerList=res.data.records;
         },
         addCustomer(){
            console.log("customer:"+this.customer);
             if(this.customer.password){
                this.customer.password=hex_md5(this.customer.password+DEFAULT_KEY);
             }
             var param=this.customer;
             var res=ajax('POST',addCustomerUrl,param);
             this.open(res);

          },
          openBalanceLog(row){
            console.log(row);
           open(openBalanceLogUrl+'?'+escape('id='+row.id+'&menuType=2'));
          },
         addBalanceLog(){
             var param=this.balanceLog;
             var res=ajax('POST',addBalanceLogUrl,param);
             this.open(res);
         },
         getProductList(type){
             if(type){
                 this.keyword='';
             }
             var param={};
             if(this.keyword){
                 param={keyword:this.keyword};
             }
              var res=ajax('POST',productListUrl,param);
              if(!res.status)
              this.open(res);
              this.productList=res.data;
          },
          addProduct(){
                   var param=this.addProd;
                    var res=ajax('POST',addProductUrl,param);
                    this.open(res);
           },
          deleteProduct(){
                   var param=this.deleteProd;
                    var res=ajax('POST',deleteProductUrl,param);
                    this.open(res);
           },
           updateProduct(){
                    var param=this.updateProd;
                     var res=ajax('POST',updateProductUrl,param);
                     this.open(res);
            },

         getLoginUser(){
             var res=ajax('GET',loginUrl, null);
             console.log(res);
             if(res.status){
                setCookie(userCookieName,res.data,120);
                return res.data;
             }else{
                this.open(res);
                return null;
             }
         },
         logOut(){
          window.location.href=logOutUrl;
        }




        },
   });


var user=mainV.getLoginUser();
$("#welcomeUser").html(user.username);
mainV.getCustomerList(1);




 });

