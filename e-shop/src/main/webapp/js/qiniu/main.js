/*global Qiniu */
/*global plupload */
/*global FileProgress */
/*global hljs */

$(function() {
    var uploader = Qiniu.uploader({
        disable_statistics_report: true,
        makeLogFunc: 1,
        runtimes: 'html5,flash,html4',
        browse_button: 'pickfiles',
        max_file_size: '10000mb',
        dragdrop: true,
        chunk_size: '4mb',
        multi_selection: !(moxie.core.utils.Env.OS.toLowerCase()==="ios"),
        uptoken: 'j2ZhHmkIkB36HwrgfNDPcpI6-03G8pFxByQZCYbK:5mRSeJcZQ-9uCqk0uIe1chx0tlk=:eyJzY29wZSI6ImVoYWlzY2hpbGRyZW4iLCJkZWFkbGluZSI6MTUwNDc4OTE2M30=',
        unique_names: true,
        max_retries: 7,                     // 上传失败最大重试次数
        domain: "http://ovug9f17p.bkt.clouddn.com/",
        get_new_uptoken: true,
        auto_start: true,
        log_level: 5,
        init: {
            'BeforeChunkUpload':function (up,file) {
                console.log("before chunk upload:",file.name);
            },
            'FilesAdded': function(up, files) {
                plupload.each(files, function(file) {
                    console.log('filetype: ' + file.type);
                    if(file.type=='image/jpeg'||file.type=='image/jpg'||file.type=='image/png'||file.type=='image/gif' || file.type=='video/x-matroska' || file.type=='video/mp4'){
                        console.log('type:' + file.type);
                    }else {
                        console.log('上传类型只能是.jpg,.png,.gif,.mkv');
                        return false;
                    }});
            },
            'BeforeUpload': function(up, file) {
                console.log("this is a beforeupload function from init");

            },
            'UploadProgress': function(up, file) {
            	console.log(file.percent+"+"+file.speed);

            },
            'UploadComplete': function() {

            },
            'FileUploaded': function(up, file, info) {
                alert(JSON.stringify(info));
                console.log("FileUploaded:"+JSON.stringify(info));
            },
            'Error': function(up, err, errTip) {

            }

        }
    });
    uploader.bind('FilesAdded', function() {
        console.log("hello man, a file added");
    });
    uploader.bind('BeforeUpload', function () {
        console.log("hello man, i am going to upload a file");
    });
    uploader.bind('FileUploaded', function () {
        console.log('hello man,a file is uploaded');
    });
    $('#up_load').on('click', function(){
        uploader.start();
    });
    $('#stop_load').on('click', function(){
        uploader.stop();
    });
    $('#retry').on('click', function(){
        uploader.stop();
        uploader.start();
    });
    $('#container').on(
        'dragenter',
        function(e) {
            e.preventDefault();
            $('#container').addClass('draging');
            e.stopPropagation();
        }
    ).on('drop', function(e) {
        e.preventDefault();
        $('#container').removeClass('draging');
        e.stopPropagation();
    }).on('dragleave', function(e) {
        e.preventDefault();
        $('#container').removeClass('draging');
        e.stopPropagation();
    }).on('dragover', function(e) {
        e.preventDefault();
        $('#container').addClass('draging');
        e.stopPropagation();
    });


});
