$(document).ready(function(){
  $("#login").click(function(){
    console.log("....")
    var userName = $("#userName").val()
    var password = $("#password").val
    axios({
      method:"POST",
      url:"http://localhost:8080/login",
      data:{
        'userName':userName,
        'password':password
      }
    }).then(response=>{
      window.location='../html/word.html'
      msg = response.data.msg;
      console.log(msg);
    })
  })
});