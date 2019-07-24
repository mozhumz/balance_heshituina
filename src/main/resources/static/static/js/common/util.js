function ajax(method,url,param){
var result=null;
$.ajax({
             type:method,
             dataType:"JSON",
             contentType: 'application/json',
             async: false,
             url:url,
             data:JSON.stringify(param),
             success:function(res){
                    if(!res.status&&res.code==10005){
                        //重定向到登录页
                        window.location.href=zuulUrl;
                    }
                    result= res;
             }

         });
    return result;
}

function formatDate(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  var o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  };
  for (var k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      var str = o[k] + '';
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
    }
  }
  return fmt;
};

function padLeftZero(str) {
  return ('00' + str).substr(str.length);
};

//获取url中的第一个参数
function getOneParam(html){
    try{
        var s=html.split('?');
        if(s.length>1){
            return s[1].split('=')[1];
        }
    }catch(Error ){

    }
    return null;
}

//获取url中的指定参数值
function getParamValue(variable)
{
        var search=window.location.search;
        if(!search){
            return null;
        }
       var query = unescape(search.substring(1));

       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return null;
}

//将java date转为js date
function getDate(date){
    if(!date){
        return null;
    }
    return new Date(date.time);
}

function getGender(gender){
    if(!gender){
        return null;
    }
    if(gender==1){
        return '男';
    }

    return '女';
}

function checkParams(){
    for (var i  of  arguments) {
        if(!i){
            return false;
        }
        if(Array.isArray(i) ){
            if(i.length==0){
                return false;
            }
        }
    }
    return true;
}
