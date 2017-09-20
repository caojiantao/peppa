/**
 * 页面初始化执行的js（放在body尾部，dom结构加载完毕执行）
 */
$(function () {
  // ajax error统一处理
  $(document).ajaxError(function (event, xhr) {
    switch (xhr.status) {
      case 500:
        alert("服务器内部错误，已通知开发人员！");
        break;
      default:
        break;
    }
  });
});