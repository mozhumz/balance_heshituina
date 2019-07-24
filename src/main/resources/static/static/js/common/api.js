var pre='http://127.0.0.1:8080/balance';
var zuulPre='http://127.0.0.1:8080/hstn';
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


var ok='操作成功';