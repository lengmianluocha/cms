<!DOCTYPE html>
<!-- saved from url=(0042)https://v3.bootcss.com/examples/jumbotron/ -->
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://v3.bootcss.com/favicon.ico">

    <title>${title}</title>

    <!-- Bootstrap core CSS -->
    <link href="http://static-1257119014.cos.ap-chengdu.myqcloud.com/assets/css/bootstrap.min.css" rel="stylesheet">
    <!--<link href="./bootstrap.min.css" rel="stylesheet">-->

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        .entry-meta {
            margin-top: .5em;
            color: #aaa;
        }

        footer {
            font-size: 12px;
            color: #999;
        }
    </style>
</head>

<body>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <div class="row-fluid">
            <div class="span4">
            </div>
            <div class="span4">
                <h2>　
                ${title}
                </h2>
                <div class="entry-meta">
                    <time class="entry-date" datetime="2019-01-31T20:44:52+00:00" pubdate=""
                          itemprop="datePublished">2019-01-31
                    </time>
                    <span class="dot">•</span>
                    <span class="categories-links" itemprop="keywords">电影</span>
                </div>
            </div>
            <div class="span4">
            </div>
        </div>
        <p>地址： <a href="${panurl}">${title}</a></p>
        <p>密码： ${panpwd}</p>

    <div class="row">
         <div class="col-md-2"></div>

         <div class="col-md-8">
            <a class="btn btn-primary btn-sm"
                   href="http://www.nitethoughts.club/moive/minvalid?moiveName=${encodetitle}" role="button">链接失效</a>

            <#--<#if '${title}'?index_of("更新至")!=-1>-->
            <a class="btn btn-primary btn-sm"
                   href="http://www.nitethoughts.club/moive/murge?moiveName=${encodetitle}" role="button">催更</a>
            <#--</#if>-->
         </div>

        <div class="col-md-2"></div>
    </div>


</div>
</div>

<div class="container">
    <!-- Example row of columns -->
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <p> 链接：${panurl}</p>
            <p> 提取码：${panpwd}</p>
            <p>复制这段内容后打开百度网盘手机App，操作更方便哦 </p>
        </div>
        <div class="col-md-4">
        </div>
    </div>

    <hr>

    <footer>
        <p>© 2019 夜舒所见</p>
        <p>
            免责声明：本系统所提供的电影链接均来源于互联网公开分享链接，仅供参考，学习，交流，试看，请支持正版。</p>
        <p>   如有侵犯您的权益，请发送相关证明文件及时与我们联系（邮箱：591672801@qq.com）,我们会第一时间进行处理。</p>
    </footer>
</div> <!-- /container -->

</body>
<script type="text/javascript">
    // 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
    var useragent = navigator.userAgent;
    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
        // 这里警告框会阻塞当前页面继续加载
        alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
        // 以下代码是用javascript强行关闭当前页面
        var opened = window.open('about:blank', '_self');
        opened.opener = null;
        opened.close();
    }
</script>
</html>
