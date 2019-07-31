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
 	 customer:{name:'',phone:'',money:'',customerNo:'',gender:null,birthDate:null,remark:'',password:null,password2:null,
 	    email:null,doUserId:null,doPassword:null},
 	 balanceLog:{customerId:null,money:null,productIds:[],type:null,remark:null,doName:null},
 	 productList:[],
     addProd:{name:null},
     deleteProd:{id:null},
     updateProd:{id:null,name:null,remark:null},
     fileList:[],
     genderList:[{type:1,name:'男'},{type:2,name:'女'}],
     isAdmin:false,
     isBalance:false,
     lazyBtn_f:false,
     //操作人列表
      doUserList:[],
      rules: {
               name: [
                                { required: true, message: '请输入姓名', trigger: 'blur' },
                                { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
                              ],
                phone: [
                            { required: true, message: '请输入手机', trigger: 'blur' },
                            { min: 8, max: 15, message: '长度在 8 到 15 个字符', trigger: 'blur' }
                        ],
                 money: [
                   { required: true, message: '请输入余额', trigger: 'blur' }
                 ],
                 customerNo: [
                          { required: true, message: '请输入编号', trigger: 'blur' }
                        ],

                doUserId: [
                          { required: true, message: '请选择操作人', trigger: 'change' }
                        ],
                doPassword: [
                          { required: true, message: '请输入操作密码', trigger: 'blur' }
                        ]
             }

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
         open2(msg) {
                      var type='error';
                      this.$message({
                          message: msg,
                          type: type
                      });
                   },
        disableBtn(type){
            if(!type){
              mainV.lazyBtn_f=true;
            }else{
              mainV.sendCodeBtn_f=true;
            }
          },
          enableBtn(type){
            setTimeout(function(){
                if(!type){
                  mainV.lazyBtn_f=false;
                }else{
                  mainV.sendCodeBtn_f=false;
                }
              },1000);
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
                    if(i.is0pwd==1){
                        i.is0pwd="是";
                    }else{
                        i.is0pwd="否";
                    }
                }
            this.customerList=res.data.records;
         },
         addCustomer(formName){
            this.disableBtn();
            this.$refs[formName].validate((valid) => {
              if (valid) {
                if(!checkParams(this.customer.customerNo,this.customer.name,this.customer.phone)){
                    this.enableBtn();
                    this.open2(paramErr);
                    return null;
                }
                 if(this.customer.password){
                    this.customer.password=hex_md5(this.customer.password+DEFAULT_KEY);
                 }
                 var param=this.customer;
                 var res=ajax('POST',addCustomerUrl,param);
                 this.enableBtn();
                 this.open(res);
              } else {
                this.enableBtn();
                this.open2(paramErr);
                return false;
              }
            });


          },
          openBalanceLog(row,menuType){
           open(openBalanceLogUrl+'?'+escape('id='+row.id+'&menuType='+menuType));
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
              for(var i of res.data){
                i.createDate=this.formatDate(i.createDate);
              }
              this.productList=res.data;
          },
          addProductHtml(){
            open(addProductHtmlUrl);
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
           updateProduct(row){
                    var param={};
                    if(row){
                        param=row;
                    }else{
                        param=this.updateProd;
                    }
                     var res=ajax('POST',updateProductUrl,param);
                     this.open(res);
            },
          submitUpload() {
            this.disableBtn();
            this.$refs.upload.submit();
            this.enableBtn();
          },
          handleRemove(file, fileList) {
            console.log(file, fileList);
          },
          handlePreview(file) {
            console.log(file);
          },
          customerSuc(res, file, fileList){
          console.log("suc");
            this.open(res);
          },
         customerErr(res, file, fileList){
          this.open(res);
        },
        customerBef(file){
            var FileExt = file.name.replace(/.+\./, "");
            if ([ 'xls', 'xlsx'] .indexOf(FileExt.toLowerCase()) === -1){
            this.$message({ type: 'warning', message: '请上传后缀名为[xls,xlsx的文件！' });
             return false;
            }
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
         getUserList(roleName){
             var param={roleName:roleName,state:1};
             var res=ajax('POST',userListUrl, param);
             if(!res.status){
                 this.open(res);
                 return null;
             }
             return res.data;
          },
         logOut(){
             window.location.href=logOutUrl;
          }




        },
   });


var user=mainV.getLoginUser();
if(user.role.name=="admin"){
    mainV.isAdmin=true;
}else if(user.role.name=="普通管理员"){
    mainV.isBalance=true;
}
$("#welcomeUser").html(user.username);
mainV.getCustomerList(1);

mainV.doUserList=mainV.getUserList('普通管理员');

 //回车键绑定
 $("body").keydown(function() {
     if (event.keyCode == "13") {//keyCode=13是回车键
         if(mainV.customerList_f){
            //客户列表
            mainV.getCustomerList();
         }else if(mainV.serviceSet_f){
            mainV.getProductList();
         }

     }
 });

 });

