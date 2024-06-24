new Vue({
    el: '#app',
    data: {
        imglist: [],
        fileList: [],
        successNum: 0,// 成功上传数量
        fileNum: 0,
        isShowInfo: false,
        time_to_load: 3
    },


    methods: {
        submitUpload() {
            console.log("上传图片:");
            this.isShowInfo = true;
            this.$refs.upload.submit();
            this.load_fun();
        },
        // 上传文件成功时触发
        handleUploadSuccess(response, file, fileList) {
            console.log("上传成功:", response);
            this.uploadResponse = `上传成功: ${JSON.stringify(response)}`;
            this.successNum++;
        },
        // 上传文件失败时触发
        handleUploadError(err, file, fileList) {
            console.error("上传失败:", err);
            this.uploadResponse = `上传失败: ${JSON.stringify(err)}`;
        },
        // 移除文件时触发
        handleRemove(file, fileList) {
            console.log("移除图片:");
            console.log(file, fileList);
            this.fileNum = fileList.length;
        },
        //预览文件时触发
        handlePreview(file) {
            console.log("预览图片:");

            console.log(file);
            /*
              file:
               {
                "status": "ready",
                "name": "QQ截图20240610002050.png",
                "size": 2648620,
                "percentage": 0,
                "uid": 1719197887736,
                "raw": {
                    "uid": 1719197887736
                }
               }
            */
            console.log("文件路径:" + URL.createObjectURL(file.raw));
            this.$alert(`<h4 style="font-size: small;">文件名:${file.name}</h4>
                        <h4 style="font-size: small;">文件大小:${file.size} 字节(Byte/B)</h4>
                        <h4 style="font-size: small;">状态:${file.status}</h4>
                        <img style="width:388px;" src="${URL.createObjectURL(file.raw)}" alt="预览图片">`, '文件预览', {
                dangerouslyUseHTMLString: true
            });


        },
        handleUploadChange(file, fileList) {
            // 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
            console.log("文件改变:");
            console.log(file, fileList);
            this.fileList = fileList;
            this.fileNum = fileList.length;
        },
        getalldata() {
            axios.get('/api/getAllimages').then(response => {
                console.log("数据获取成功:");
                console.log(response.data);
                this.imglist = response.data.data;
            }).catch(error => {
                console.log("数据获取失败:");
                console.log(error);
            });
        },
        clearSelected() {
            this.fileList = [];
        },
        showImg(imgUrl) {
            console.log("显示图片:");
            console.log(imgUrl);
            this.$alert(`
                <h4 style="font-size: small;">图片地址:${imgUrl}</h4>  
                <h4 style="font-size: small;">图片尺寸:388x388</h4>
                <h4 style="font-size: small;">图片格式:png</h4>
                <img style="width:785px;" src="${imgUrl}" alt="预览图片">`, '图片预览', {
                dangerouslyUseHTMLString: true,
                customClass: 'custom-alert'
            });
        },
        deleteAll() {
            axios.get('/api/deleteAllImages').then((response) => {
                console.log("数据删除成功:");
                console.log(response.data);
                // this.imglist = [];
                this.isShowInfo = true;
                this.load_fun();
                this.$message({
                    message: '数据删除成功',
                    type: 'success'
                });
            }).catch((error) => {
                console.log("数据删除失败:");
                console.log(error);
                this.$message({
                    message: '数据删除失败',
                    type: 'error'
                });
            });
        },
        load_fun() {
            //setInterval: 这是一个非阻塞的函数，会在每隔 1000 毫秒（1 秒）执行一次回调函数。
            let timer = setInterval(() => {
                this.time_to_load--;
                if (this.time_to_load === 0) {
                    this.isShowInfo = false;
                    this.time_to_load = 3;
                    //销毁定时器
                    clearInterval(timer);
                }
            }, 1000)


            setTimeout(() => {
                this.getalldata();
                this.isShowInfo = false;
            }, 3000)
        },
        getFileType(filename) {
            // 定义图片和视频的扩展名数组
            const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.tiff', '.svg'];
            const videoExtensions = ['.mp4', '.avi', '.mov', '.wmv', '.flv', '.mkv', '.webm', '.mpeg', '.mpg'];

            // 提取文件名的扩展名
            const fileExtension = filename.slice((filename.lastIndexOf('.') - 1 >>> 0) + 2).toLowerCase();

            // 判断扩展名是否在图片扩展名数组中
            if (imageExtensions.includes(`.${fileExtension}`)) {
                return 'image';
            }

            // 判断扩展名是否在视频扩展名数组中
            if (videoExtensions.includes(`.${fileExtension}`)) {
                return 'video';
            }

            // 如果都不匹配，返回未知类型
            return 'unknown';
        },
        isImage(filename) {
            const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.tiff', '.svg'];
            const fileExtension = filename.slice((filename.lastIndexOf('.') - 1 >>> 0) + 2).toLowerCase();
            return imageExtensions.includes(`.${fileExtension}`);
        },
        isVideo(filename) {
            const videoExtensions = ['.mp4', '.avi', '.mov', '.wmv', '.flv', '.mkv', '.webm', '.mpeg', '.mpg'];
            const fileExtension = filename.slice((filename.lastIndexOf('.') - 1 >>> 0) + 2).toLowerCase();
            return videoExtensions.includes(`.${fileExtension}`);
        }
    },


    created() {
        this.getalldata();
    }
    ,

})
;