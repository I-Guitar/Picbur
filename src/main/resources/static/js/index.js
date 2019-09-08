const RESULT_TEMP = "<div><img class=\"result-img\" src=\"{LINK}\" alt=\"img\"/><br/><input class=\"result-input\" type=\"text\" value=\"{LINK}\"/></div>";

$(function () {
    $("#commit").on("click", function () {
        let formData = new FormData();
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
                $("#result-div").append($(RESULT_TEMP.replace(/{LINK}/g, data)))
            },
            error: function (data) {
                window.alert("Failed! => " + data);
            }
        });
    });

    $("#result-div").on("click", ".result-input", copyValue);

    /**
     * 拷贝输入框中的值到剪切板
     * */
    function copyValue() {
        if (window.clipboardData) {
            window.clipboardData.clearData();
            window.clipboardData.setData("Text", this.value);
            alert("复制成功");
        } else {
            $("#result-div").unbind();
            alert("请手动复制！")
        }

    }

});




