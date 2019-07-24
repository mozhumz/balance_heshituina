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
 	 welcomeUser:'',
 	 keyword:'',
 	 customer:{name:'',phone:'',money:'',customerNo:'',gender:null,birthDate:null,remark:'',password:null,password2:null},
 	 balanceLog:{customerId:customerId,money:null,productUserList:[],type:null,remark:null,doUserId:null,password:null,empPassword:null},
 	 customer:{name:null,phone:null,money:null,password:null,customerNo:null,gender:null,birthDate:null,remark:null,createDate:null},
 	 customerBaseInfo:'客户基本信息(姓名，编号，手机)：',
 	 customerId:customerId,
 	 balanceLogList:[],
 	 //消费项目列表
 	 productList:[{id:1,name:"test"},{id:2,name:"test2"},{id:3,name:"test3"},{id:4,name:"test4"}],
 	 //服务员工列表
 	 empList:[{id:1,username:'zhangsan',realName:'张三'},{id:2,username:'zhangsan2',realName:'张三2'}],
 	 consumeTypeList:[{type:1,name:'消费'},{type:2,name:'充值'}],
 	 consumeType:null,
 	 menuActive: menuType+''


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
                     this.getCustomer();
                     break;
                case 2:
                    //添加消费
                     this.balanceLogList_f=false;
                     this.customerDetail_f=false;
                     this.addBalanceLog_f=true;
                    break;
                case 3:
                //历史消费
                    this.balanceLogList_f=true;
                     this.customerDetail_f=false;
                     this.addBalanceLog_f=false;
                     this.getBalanceLogList();
                    break;
                default:
                    this.balanceLogList_f=false;
                     this.customerDetail_f=true;
                     this.addBalanceLog_f=false;
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
             res.data.gender=getGender(res.data.gender);
             res.data.createDate=this.formatDate(res.data.createDate);
             res.data.birthDate=this.formatDate(res.data.birthDate);
             this.customer=res.data;

          },
          getPwd(pwd){
            if(pwd){
                return hex_md5(pwd+DEFAULT_KEY);
            }
            return null;
          },
         addBalanceLog(){
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
                this.open2('您选择的服务员工有重复数据');
                return false;
             }
             if(this.balanceLog.type==1&&(!list||list.length==0)){
                this.open2('必须选择消费项目和服务员工');
                return false;
             }
             //参数判空
            if(!checkParams(this.balanceLog.type,this.balanceLog.money,this.balanceLog.doUserId,
                this.balanceLog.empPassword,this.balanceLog.password)){
                this.open2('必填项有空值');
                return false;
            }

             this.balanceLog.productUserList=list;

             this.balanceLog.password=this.getPwd(this.balanceLog.password);
             this.balanceLog.empPassword=this.getPwd(this.balanceLog.empPassword);
             this.balanceLog.money=parseFloat(this.balanceLog.money);
             var param=this.balanceLog;
             var res=ajax('POST',addBalanceLogUrl,param);
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
         }




        },
   });

//登录信息
var user=mainV.getLoginUser();
$("#welcomeUser").html(user.username);
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





 });

