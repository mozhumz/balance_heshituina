var userCookieName='heshituina-user';
var roleCkName='heshituina-role';
var customerCKName='heshituina-customer';
var usermanageUrl='../usermanage/index.html';
var zuulUrl='../hstn/index.html';
var openBalanceLogUrl='addBalanceLog.html';
var addProductHtmlUrl='addProduct.html';


//liveMinutes:分钟
function setCookie(name, value, liveMinutes) {
	if (liveMinutes == undefined || liveMinutes == null) {
		liveMinutes = 60 * 2;
	}
	if (typeof (liveMinutes) != 'number') {
		liveMinutes = 60 * 2;//默认120分钟
	}
	var minutes = liveMinutes * 60 * 1000;
	var exp = new Date();
	exp.setTime(exp.getTime() + minutes + 8 * 3600 * 1000);
	//path=/表示全站有效，而不是当前页
	document.cookie = name + "=" + JSON.stringify(value) + ";path=/;expires=" + exp.toUTCString();
}

//读取cookie
function getCookieByName(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

function getCookie(name){

    return $.parseJSON(getCookieByName(name));
}

//删除cookie
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}