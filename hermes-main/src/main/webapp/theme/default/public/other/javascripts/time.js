$(function(){
updateEndTime();
});
//����ʱ����
function updateEndTime()
{
 var date = new Date();
 var time = date.getTime();  //��ǰʱ���1970��1��1��֮��ĺ�����
 
 $(".settime").each(function(i){
 
 var endDate =this.getAttribute("endTime"); //����ʱ���ַ���
 //ת��Ϊʱ����������
 var endDate1 = eval('new Date(' + endDate.replace(/\d+(?=-[^-]+$)/, function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');

 var endTime = endDate1.getTime(); //����ʱ�������
 
 var lag = (endTime - time) / 1000; //��ǰʱ��ͽ���ʱ��֮�������
  if(lag > 0)
  {
   var second = Math.floor(lag % 60);     
   var minite = Math.floor((lag / 60) % 60);
   var hour = Math.floor((lag / 3600) % 24);
   var day = Math.floor((lag / 3600) / 24);
   $(this).html(day+"��"+hour+"Сʱ"+minite+"��"+second+"��");
  }
  else
   $(this).html("�Ѿ���������");
 });
 setTimeout("updateEndTime()",1000);
}