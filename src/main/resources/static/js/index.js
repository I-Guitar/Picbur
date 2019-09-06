$(function () {
    $("#commit").on("click", function () {
        var formData = new FormData();
        formData.append('image', $('#image')[0].files[0]);  //添加图片信息的参数
        // formData.append('sizeid', 123);  //添加其他参数
        $.ajax({
            url: '/imageUpload',
            type: 'POST',
            cache: false, // 上传文件不需要缓存
            data: formData,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success: function (data) {
                var rs = eval("(" + data + ")");
                if (rs.state == 1) {
                    tipTopShow('上传成功！');
                } else {
                    tipTopShow(rs.msg);
                }
            },
            error: function (data) {
                tipTopShow("上传失败");
            }
        });
    });
});




