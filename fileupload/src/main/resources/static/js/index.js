//关闭浏览器右键默认
document.addEventListener('contextmenu', function (event) {
    event.preventDefault();
});

new Vue({
    el: '#app',
    data: {
        imglist: [], 
        fileList: [], 
        successNum: 0,// 成功上传数量
        fileNum: 0,
        isShowInfo: false, 
        time_to_load: 3,
        show: false,//用于加载element ui的加载效果
    },

    methods: {
        downloadImg(id) {
            console.log(id);
            // 调用文件下载函数
            this.downloadMethod(id);
        },

        downloadMethod(id) {
            axios.get('/api/getImgFileById/' + id, {
                responseType: 'blob'
            }).then(response => {
                // 创建一个 Blob 对象
                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;

                // 获取文件名，可以根据你的 API 返回的数据来获取文件名
                const contentDisposition = response.headers['content-disposition'];
                let fileName = 'downloaded_image.png';
                if (contentDisposition) {
                    const fileNameMatch = contentDisposition.match(/filename="(.+)"/);
                    if (fileNameMatch.length === 2) {
                        fileName = fileNameMatch[1];
                    }
                }

                link.setAttribute('download', fileName);
                document.body.appendChild(link);
                link.click();
                link.remove();

                // 显示下载成功的消息
                this.$message({
                    message: '下载成功',
                    type: 'success'
                });
            }).catch(error => {
                console.log("下载失败:");
                console.log(error);

                // 显示下载失败的消息
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            });
        }
        ,
        submitUpload() {
            console.log("上传图片:");
            this.isShowInfo = true;
            this.$refs.upload.submit();
            this.load_fun();
        }, // 上传文件成功时触发
        handleUploadSuccess(response, file, fileList) {
            console.log("上传成功:", response);
            this.uploadResponse = `上传成功: ${JSON.stringify(response)}`;
            this.successNum++;
        }, // 上传文件失败时触发
        handleUploadError(err, file, fileList) {
            console.error("上传失败:", err);
            this.uploadResponse = `上传失败: ${JSON.stringify(err)}`;
        }, // 移除文件时触发
        handleRemove(file, fileList) {
            console.log("移除图片:");
            console.log(file, fileList);
            this.fileNum = fileList.length;
        }, //预览文件时触发
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

            // 判断文件类型是否是图片或视频
            if (this.isImage(file.name) || this.isVideo(file.name)) {
                if (this.isImage(file.name)) {
                    this.$alert(`<h4 style="font-size: small;">文件名:${file.name}</h4>
                        <h4 style="font-size: small;">文件大小:${file.size} 字节(Byte/B)</h4>
                        <h4 style="font-size: small;">状态:${file.status}</h4>
                        <img style="width:388px;" src="${URL.createObjectURL(file.raw)}" alt="预览图片">`, '文件预览', {
                        dangerouslyUseHTMLString: true
                    });
                } else {
                    this.$alert(`<h4 style="font-size: small;">文件名:${file.name}</h4>
                        <h4 style="font-size: small;">文件大小:${file.size} 字节(Byte/B)</h4>
                        <h4 style="font-size: small;">状态:${file.status}</h4>
                         <video   style="width:388px;" controls>
                            <source src="${URL.createObjectURL(file.raw)}" type="video/mp4">
                        </video>`, '文件预览', {
                        dangerouslyUseHTMLString: true
                    });
                }

            } else {
                this.$alert(`<h4 style="font-size: small;">文件名:${file.name}</h4>
                        <h4 style="font-size: small;">文件大小:${file.size} 字节(Byte/B)</h4>
                        <h4 style="font-size: small;">状态:${file.status}</h4>
                        <h1 style="color: red;">文件无法预览，请确认文件类型是否是图片或视频！</h1>
                       `, '文件预览', {
                    dangerouslyUseHTMLString: true
                });
            }


        }, handleUploadChange(file, fileList) {
            // 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
            console.log("文件改变:");
            console.log(file, fileList);
            this.fileList = fileList;
            this.fileNum = fileList.length;
        }, getalldata() {
            axios.get('/api/getAllimages').then(response => {
                console.log("数据获取成功:");
                console.log(response.data);
                this.imglist = response.data.data;
                this.show = false;
                setTimeout(() => {
                    this.show = true;
                }, 1)
            }).catch(error => {
                console.log("数据获取失败:");
                console.log(error);
            });
        }, clearSelected() {
            this.fileList = [];
        }
        ,

        showImg(imgUrl) {
            console.log("显示图片:");
            console.log(imgUrl);

            /*
            dangerouslyUseHTMLString: true:
                允许在弹出框中使用 HTML 字符串。如果不设置为 true，HTML 内容会被转义为普通文本。
            customClass: 'custom-alert':
                为弹出框应用自定义 CSS 类 custom-alert，用于自定义样式
            */
            this.$alert(`
                <h4 style="font-size: small;">图片地址:${imgUrl}</h4>  
                <h4 style="font-size: small;">图片尺寸:388x388</h4>
                <h4 style="font-size: small;">图片格式:png</h4>
                <img style="width:785px;" src="${imgUrl}" alt="预览图片">`, '图片预览', {
                dangerouslyUseHTMLString: true, customClass: 'custom-alert'
            });
        }
        ,

        deleteAll() {
            axios.get('/api/deleteAllImages').then((response) => {
                console.log("数据删除成功:");
                console.log(response.data);
                // this.imglist = [];
                this.isShowInfo = true;
                this.load_fun();
                this.$message({
                    message: '数据删除成功', type: 'success'
                });
            }).catch((error) => {
                console.log("数据删除失败:");
                console.log(error);
                this.$message({
                    message: '数据删除失败', type: 'error'
                });
            });
        },
        deleteAllNotVideoAndImage() {
            axios.get('/api/deleteAllFilesNotImgNotVideo').then((response) => {
                console.log("数据删除成功:");
                console.log(response.data);
                // this.imglist = [];
                this.isShowInfo = true;
                this.load_fun();
                this.$message({
                    message: '数据删除成功', type: 'success'
                });
            }).catch((error) => {
                console.log("数据删除失败:");
                console.log(error);
                this.$message({
                    message: '数据删除失败', type: 'error'
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
        }
        ,

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
        }
        ,

        isImage(filename) {
            const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.tiff', '.svg', '.jfif'];
            const fileExtension = filename.slice((filename.lastIndexOf('.') - 1 >>> 0) + 2).toLowerCase();
            return imageExtensions.includes(`.${fileExtension}`);
        }
        ,
        isVideo(filename) {
            const videoExtensions = ['.mp4', '.avi', '.mov', '.wmv', '.flv', '.mkv', '.webm', '.mpeg', '.mpg'];
            const fileExtension = filename.slice((filename.lastIndexOf('.') - 1 >>> 0) + 2).toLowerCase();
            return videoExtensions.includes(`.${fileExtension}`);
        },
        handleKeydown(event) {
            if (event.key === "Enter") {
                this.message = "You pressed Enter on the image!";
            } else {
                this.message = `You pressed ${event.key} on the image.`;
            }
        }
    },


    created() {
        this.getalldata();
    },

});