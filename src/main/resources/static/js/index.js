$(function () {
    functionRegister();

    init();

    $("#commit").on("click", function () {
        $("#masking-bg").removeAttr("hidden");
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
                $("#masking-bg").attr("hidden", "hidden");
            },
            error: function (data) {
                $("#masking-bg").attr("hidden", "hidden");
                window.alert("Failed! => " + data);
            }
        });
    });

    /**
     * 点击拷贝链接
     * */
    $("body").on("click", ".copy", function (e) {
        let urlDom = $(e.target).parents(".col-md-4").find("span.url");
        let cacheInput = document.getElementById("copy-cache");
        cacheInput.value = urlDom.text();
        cacheInput.select();
        document.execCommand("Copy");
        $('.alert').html('复制成功').addClass('alert-success').show().delay(1500).fadeOut();
    });

    /**
     * 方法注册
     * */
    function functionRegister() {
        Date.prototype.format = function (fmStr) {
            return dateFormat(fmStr, this);
        }
    }

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
        let lasUploadDom = $("#last-upload");
        // 设置图片
        let imgDom = lasUploadDom.find("img");
        imgDom.attr("src", record.url + "?x-oss-process=image/resize,m_pad,h_200,w_200");
        // 设置url
        let urlDom = lasUploadDom.find(".url");
        urlDom.text(record.url);
        urlDom.attr("title", record.url);
        // 设置time
        let timeDom = lasUploadDom.find(".time");
        let formatTime = new Date(record.uploadTime).format("yyyy-MM-dd HH:mm:ss");
        timeDom.text(formatTime);
        timeDom.attr("title", formatTime);

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
        clone.removeAttr("hidden");
        // 设置图片
        clone.find("img").attr("src", record.url + "?x-oss-process=image/resize,m_pad,h_200,w_200");
        // 设置url
        let urlDom = clone.find(".url");
        urlDom.text(record.url);
        urlDom.attr("title", record.url);
        // 设置time
        let timeDom = clone.find(".time");
        let formatTime = new Date(record.uploadTime).format("yyyy-MM-dd HH:mm:ss");
        timeDom.text(formatTime);
        timeDom.attr("title", formatTime);

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

    /**
     * 时间格式化
     * */
    function dateFormat(fmt, date) {
        let ret;
        const opt = {
            "y+": date.getFullYear().toString(),        // 年
            "M+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "H+": date.getHours().toString(),           // 时
            "m+": date.getMinutes().toString(),         // 分
            "s+": date.getSeconds().toString()          // 秒
            // 有其他格式化字符需求可以继续添加，必须转化成字符串
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            }
        }
        return fmt;
    }

});




