 //添加消费-消费项目&服务员工的index 第一行为0
  	 var selectIndex=1;
  	 //删除li
  	 function removeLi(btn){
         $(btn).parent().remove();
     }
     //组装消费项目&服务员工
     function productEmpAssemble(s,obj,type){
        var optionList=$(s).children('option');
        for(var op of optionList){
            if(op.selected){
                if(type==1){
                    obj.productIdList.push(parseInt(op.value));
                }else{
                    if(op.value!="0")
                    obj.userId=parseInt(op.value);
                }

            }
        }
     }
     //判断数组是否有重复元素
     function isRepeat(arr){
        if(!arr||arr.length==0){
            return false;
        }
         var hash = {};
         for(var i in arr) {
             if(hash[arr[i]])
             return true;
             hash[arr[i]] = true;
         }
        return false;
     }

 $(function(){

var customerId=getParamValue('id');
var menuType=getParamValue('menuType');
if(customerId){
    customerId=parseInt(customerId);
}
if(menuType){
    menuType=parseInt(menuType);
}else{
    menuType=1;
}
  var mainV=new Vue({
   el: '#main',
   data:{
 	 tabPosition: 'left',
 	 //表格数据
 	 customerList: [],
 	 //添加消费记录
 	 addBalanceLog_f:false,
 	 //历史消费列表
 	 balanceLogList_f:false,
 	 //客户详情
 	 customerDetail_f:true,
 	 //客户密码
 	 customerPwd_f:true,
 	 welcomeUser:'',
 	 keyword:'',
 	 customer:{name:'',phone:'',money:'',customerNo:'',gender:null,birthDate:null,remark:'',password:null,password2:null},
 	 balanceLog:{customerId:customerId,money:null,productUserList:[],type:null,remark:null,doUserId:null,password:null,empPassword:null},
 	 customer:{name:null,phone:null,money:null,password:null,password2:null,customerNo:null,gender:null,birthDate:null,
 	    remark:null,createDate:null,email:null,emailCode:null},
 	 customerBaseInfo:'客户基本信息(姓名，编号，手机)：',
 	 customerId:customerId,
 	 balanceLogList:[],
 	 //消费项目列表
 	 productList:[],
 	 //服务员工列表
 	 empList:[],
 	 consumeTypeList:[{type:1,name:'消费'},{type:2,name:'充值'}],
 	 genderList:[{type:1,name:'男'},{type:2,name:'女'}],
 	 consumeType:null,
 	 menuActive: menuType+'',
 	 isAdmin:false,
     isBalance:false,
     lazyBtn_f:false,
     sendCodeBtn_f:false,
     sendCodeTxt:'发送验证码（有效期为5分钟）',
     sendCode_f:true,
     code_time:60,
     //操作人列表
     doUserList:[]


   },
//   filters:{
//
//   },
   methods:{
        changeMenu(menuType){
            switch(menuType){
                case 1:
                //客户详情
                 	 this.balanceLogList_f=false;
                 	 this.customerDetail_f=true;
                 	 this.addBalanceLog_f=false;
                 	 this.customerPwd_f=false;
                     this.getCustomer();
                     break;
                case 4:
                     //客户密码
                     this.balanceLogList_f=false;
                     this.customerDetail_f=false;
                     this.addBalanceLog_f=false;
                     this.customerPwd_f=true;
                      break;
                case 2:
                    //添加消费
                    if(this.isBalance){

                     this.balanceLogList_f=false;
                     this.customerDetail_f=false;
                     this.addBalanceLog_f=true;
                     this.customerPwd_f=false;
                    }else{
                        this.changeMenu(-1);
                    }
                    break;
                case 3:
                //历史消费
                    this.balanceLogList_f=true;
                     this.customerDetail_f=false;
                     this.addBalanceLog_f=false;
                     this.getBalanceLogList();
                     this.customerPwd_f=false;
                    break;
                default:
                    this.balanceLogList_f=false;
                     this.customerDetail_f=true;
                     this.addBalanceLog_f=false;
                     this.customerPwd_f=false;
                     this.menuActive="1";
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
          enableBtn(type){
              setTimeout(function(){
                  if(!type){
                    mainV.lazyBtn_f=false;
                  }else{
                    mainV.sendCodeBtn_f=false;
                  }
                },1000);
          },
          disableBtn(type){
            if(!type){
              mainV.lazyBtn_f=true;
            }else{
              mainV.sendCodeBtn_f=true;
            }
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
         //历史消费列表
         getBalanceLogList(type){
            if(type){
                this.keyword='';
            }
            var param={page:1,size:20};
            if(this.keyword){
                param.keyword=this.keyword;
            }
            var res=ajax('POST',balanceLogsUrl,param);

            if(!res.status){
                this.open(res);
                return false;
            }
               this.balanceLogList=res.data.records;
            for(var i of this.balanceLogList){
                i.createDate=this.formatDate(i.createDate);
                if(i.type==1){
                    i.type='消费';
                }else{
                    i.type='充值';
                }
            }
         },
         getCustomer(){
             var param={id:this.customerId};
             var res=ajax('POST',customerUrl,param);
             if(!res.status)
             this.open(res);
//             res.data.gender=getGender(res.data.gender);
//             res.data.createDate=this.formatDate(res.data.createDate);
//             res.data.birthDate=this.formatDate(res.data.birthDate);
             this.customer=res.data;

          },
          updateCustomer(){
               this.disableBtn();
              if(!checkParams(this.customer.customerNo,this.customer.name,this.customer.phone)){
                  this.open2(paramErr);
                  this.enableBtn();
                  return null;
              }
              var param=this.customer;
              var res=ajax('POST',updateCustomerUrl,param);
              this.open(res);
              this.enableBtn();

          },
          changePwd(){
            this.disableBtn();
            if(!checkParams(this.customer.password,this.customer.password2,this.customer.emailCode)){
                this.open2(paramErr);
                this.enableBtn();
                return null;
            }
            if(this.customer.password!=this.customer.password2){
                this.open2('两次输入的密码不一致');
                this.enableBtn();
                return null;
            }
            if(this.customer.password=="123456"){
                this.open2('密码不能为初始密码：123456');
                this.enableBtn();
                return null;
            }
            this.customer.password=hex_md5(this.customer.password+DEFAULT_KEY);
            this.updateCustomer();
          },
          //发送验证码（有效期为5分钟）
          sendEmailCode(){

              this.sendCodeBtn_f=true;
              if(this.sendCode_f){
                  var timer = setInterval(function () {

                      if(mainV.code_time == 60 && mainV.sendCode_f){
                          mainV.sendCode_f = false;
                            if(!mainV.customer.email){
                                  mainV.open2('该客户未设置邮箱，请先到客户编辑菜单设置');
                                  mainV.sendCode_f = true;
                                  mainV.code_time = 60;
                                  clearInterval(timer);
                                  mainV.enableBtn(1);
                                  return null;
                          }
                          var param={receiveEmail:mainV.customer.email,customerId:mainV.customer.id};
                          var res=ajax('POST',sendEmailCodeUrl,param);
                          if(!res.status){
                                 mainV.sendCode_f = true;
                                mainV.code_time = 60;
                                clearInterval(timer);
                                mainV.enableBtn(1);
                          }
                          mainV.open(res);
                    }else if(mainV.code_time == 0){
                          mainV.sendCodeTxt='发送验证码（有效期为5分钟）';
                          mainV.enableBtn(1);
                          clearInterval(timer);
                          mainV.code_time = 60;
                          mainV.sendCode_f = true;
                      }else {
                         mainV.sendCodeTxt=mainV.code_time + " s 重新发送";
                          mainV.code_time--;
                      }
                  },1000);
               }

          },
          getPwd(pwd){
            if(pwd){
                return hex_md5(pwd+DEFAULT_KEY);
            }
            return null;
          },
         addBalanceLog(){
             this.disableBtn();
             var list=[];
             var empList=[];
             var liList=$("#chooseDiv").children('li');
             for(var li of liList){
                var obj={userId:null,productIdList:[]};
                var selectList=$(li).children('select');
                productEmpAssemble(selectList[0],obj,1);
                productEmpAssemble(selectList[1],obj,2);
                if(obj.userId&&obj.productIdList){
                    list.push(obj);
                }

                if(obj.userId){
                    empList.push(obj.userId);
                }

             }
             if(isRepeat(empList)){
                this.enableBtn();
                this.open2('您选择的服务员工有重复数据');
                return false;
             }
             if(this.balanceLog.type==1&&(!list||list.length==0)){
                this.enableBtn();
                this.open2('必须选择消费项目和服务员工');
                return false;
             }
             //参数判空
            if(!checkParams(this.balanceLog.type,this.balanceLog.money,this.balanceLog.doUserId,
                this.balanceLog.empPassword,this.balanceLog.password)){
                this.enableBtn();
                this.open2('必填项有空值');
                return false;
            }

             this.balanceLog.productUserList=list;

             this.balanceLog.password=this.getPwd(this.balanceLog.password);
             this.balanceLog.empPassword=this.getPwd(this.balanceLog.empPassword);
             this.balanceLog.money=parseFloat(this.balanceLog.money);
             var param=this.balanceLog;
             var res=ajax('POST',addBalanceLogUrl,param);
             this.enableBtn();
             this.open(res);
         },
        chooseProduct(items){
            console.log(this.balanceLog.productIds);

        },
        addChooseItem(){
            var selectId1='select'+selectIndex+1;
            var selectId2='select'+selectIndex+2;
            var item=['<li >',
                     '                    <select id="'+selectId1+'"  title="请选择消费项目" data-actions-box="true" class="selectpicker bla bla bli" multiple data-live-search="true">',
                     '                    </select>',
                     '                    &nbsp;&nbsp;&nbsp;',
                     '                    <select  id="'+selectId2+'" title="请选择服务员工"   class="selectpicker bla bla bli"  data-live-search="true">',
                     '                        <option data-hidden="true" value="0" disable>请选择服务员工</option>',
                     '                    </select>',
                     '<button class="button black" onclick="removeLi(this)">×</button>',
                     '                </li>'].join("");
             $("#chooseDiv").append(item);
//             var selectList=$("#ss").children('select');
//             console.log(selectList);
//             console.log(selectList[0]);
             for(var p of this.productList){
                $("#"+selectId1).append('<option value="'+p.id+'">'+p.name+'</option>');
             }
             for(var e of this.empList){
                $("#"+selectId2).append('<option value="'+e.id+'">'+e.realName+'</option>');
             }



            $('.selectpicker').selectpicker('refresh');
            $('.selectpicker').selectpicker('render');
            selectIndex=selectIndex+1;
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
         },
         getProductList(){
            var param={};
            var res=ajax('POST',productListUrl, param);
            if(!res.status){
                this.open(res);
                return null;
            }
            this.productList=res.data;

         },
         getEmpList(){
            var param={roleName:'员工'};
            var res=ajax('POST',userListUrl, param);
            if(!res.status){
                this.open(res);
                return null;
            }
            this.empList=res.data;
         },
        getUserList(roleName){
            var param={roleName:roleName,state:1};
            var res=ajax('POST',userListUrl, param);
            if(!res.status){
                this.open(res);
                return null;
            }
            return res.data;
         }



        },
   });

//登录信息
var user=mainV.getLoginUser();
$("#welcomeUser").html(user.username);
if(user.role.name=="admin"){
    mainV.isAdmin=true;
}else if(user.role.name=="普通管理员"){
    mainV.isBalance=true;
}
mainV.changeMenu(menuType);
//客户信息
mainV.getCustomer();
if(!mainV.customer.customerNo){
    mainV.customer.customerNo='';
}
mainV.customerBaseInfo=mainV.customerBaseInfo+mainV.customer.name+','+mainV.customer.customerNo+','+mainV.customer.phone;
$("#baseInfo").text(mainV.customerBaseInfo);

//消费项目列表 员工列表
mainV.getEmpList();
mainV.getProductList();

for(var p of mainV.productList){
    $("#select01").append('<option value="'+p.id+'">'+p.name+'</option>');
 }
 for(var e of mainV.empList){
    $("#select02").append('<option value="'+e.id+'">'+e.realName+'</option>');
 }
 $('.selectpicker').selectpicker('refresh');
 $('.selectpicker').selectpicker('render');
mainV.doUserList=mainV.getUserList('普通管理员');

//回车键绑定
 $("body").keydown(function() {
     if (event.keyCode == "13") {//keyCode=13是回车键
         if(mainV.balanceLogList_f){
            //客户列表
            mainV.getBalanceLogList();
         }

     }
 });



 });

