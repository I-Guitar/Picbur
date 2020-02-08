$(function () {

    init();

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
                uploadSuccess(data);
            },
            error: function (data) {
                window.alert("Failed! => " + data);
            }
        });
    });

    /**
     * 初始化
     * */
    function init() {
        loadUploadRecords();
    }

    /**
     * 上传成功处理
     * */
    function uploadSuccess(record) {
        $("#last-img").attr("src", record.url);
        prependToRecordDiv(record);
        recordLimit();
    }

    /**
     * 加载上传记录
     * */
    function loadUploadRecords() {
        $.ajax({
            url: '/uploadRecords',
            type: 'GET',
            success: function (records) {
                for (let i in records) {
                    let record = records[i];
                    prependToRecordDiv(record);
                }
                recordLimit();
            },
            error: function (data) {
                window.alert("load upload records failed! => " + data);
            }
        });
    }

    /**
     * 记录添加到历史记录div
     * */
    function prependToRecordDiv(record) {
        let clone = $("#upload-records-div>div:nth-child(1)").clone();
        clone.find("img").attr("src", record.url);
        clone.removeAttr("hidden");
        clone.find("p.url").text(record.url);
        $("#upload-records-div").prepend(clone);
    }

    /**
     * 记录限制
     *
     * 限制显示的记录数
     * */
    function recordLimit() {
        let records = $("#upload-records-div>div");
        for (let i = 0; i < records.length - 9; i++) {
            $("#upload-records-div>div:last-child").remove();
        }
    }

});




