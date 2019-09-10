var pre='http://127.0.0.1:8082';
//var pre='http://mozhu.iok.la';
var zuulPre='http://127.0.0.1:8080/hstn';

//var pre='http://ec2-52-196-36-65.ap-northeast-1.compute.amazonaws.com';
//var zuulPre='http://ec2-13-230-243-231.ap-northeast-1.compute.amazonaws.com/hstn';

var logOutUrl=zuulPre+'/api/login/logOut';
var loginHtml=zuulPre+'/index.html';
//获取登录用户-Get
var loginUrl=pre+'/api/user/login';
//获取客户列表
var customersUrl=pre+'/api/balance/getCustomerList';
//获取客户详情
var customerUrl=pre+'/api/balance/getCustomer';
//新增客户
var addCustomerUrl=pre+'/api/balance/addCustomer';
//添加消费
var addBalanceLogUrl=pre+'/api/balance/addBalanceLog';
//消费历史
var balanceLogsUrl=pre+'/api/balance/getBalanceLogList';
//消费项目列表
var productListUrl=pre+'/api/balance/getAllProductList';
//添加项目
var addProductUrl=pre+'/api/balance/addProduct';
//删除项目
var deleteProductUrl=pre+'/api/balance/deleteProduct';
//修改项目
var updateProductUrl=pre+'/api/balance/updateProduct';
//获取项目
var getProductUrl=pre+'/api/balance/getProduct';
//获取用户列表
var userListUrl=pre+'/api/user/getUserList';

//修改客户
var updateCustomerUrl=pre+'/api/balance/updateCustomer';
//发送验证码
var sendEmailCodeUrl=pre+'/api/balance/sendEmailCode';


var ok='操作成功';
var paramErr='必填项有空值';