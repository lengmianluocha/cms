<!DOCTYPE html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="pc,mobile">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" href="http://static-1257119014.cos.ap-chengdu.myqcloud.com/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="http://static-1257119014.cos.ap-chengdu.myqcloud.com/assets/css/ace.min.css"/>
    <meta name="keywords" content="电影,"/>
    <meta name="description" content=""/>
    <style>
        body {
            text-align: center
        }

        #divcss5 {
            margin: 0 auto;
        }

        #copycont {
            margin: 0 auto;
            border: 1px solid #000;
            width: 350px;
            white-space: normal;
            word-break: break-all;
            word-wrap: break-word;
        }
    </style>
    <title>${title}</title>
    <meta name='robots' content='noindex,follow'/>
    <link rel='dns-prefetch' href=''/>
</head>

<body class="post-template-default single single-post postid-69201 single-format-standard" itemprop="mainEntityOfPage">
<header id="header" role="banner" itemscope itemtype="http://schema.org/WPHeader">
    <div class="connect"></div>
</header>

<div id="container" class="clearfix onecolumn">
    <div class="breadcrumb" itemprop="breadcrumb">
    </div>
</div>

<main id="main" class="hfeed" role="main" itemprop="mainEntityOfPage">

    <header class="entry-header">
        <h2 class="entry-title" itemprop="name headline" align="center">${title}</h2>
    </header>


    <#--<div>-->
        <#--<p>又名：${tags}</p>-->
    <#--</div>-->

    <div class="entry-content">
        <p style="padding-left: 20px;">地址：<a href="${panurl}">${title}</a></p>
        <p style="padding-left: 20px;">密码：${panpwd}</p>
        <br/>
        <br/>

        <div id="copycont">

            链接：${panurl}

            提取码：${panpwd}

            复制这段内容后打开百度网盘手机App，操作更方便哦
        </div>

        <a href="http://www.nitethoughts.club/moive/minvalid?moiveName=${encodetitle}" class="btn btn-minier btn-yellow"
           style="margin-bottom: 20px;margin-top: 20px;">
            链接失效</a>
	<#if '${title}'?index_of("更新至")!=-1>
    <a href="http://www.nitethoughts.club/moive/murge?moiveName=${encodetitle}"
       style="margin-left: 20px;margin-bottom: 20px;margin-top: 20px;" class="btn btn-minier btn-yellow"> 催更</a>
    </#if>

        <!--<a href="http://localhost:8081/moive/minvalid?moiveName=22" style="padding-left: 20px;" > 链接失效</a>-->
        <!--<a href="http://localhost:8081/moive/murge?moiveName=22" style="padding-left: 60px;">  催更</a>-->


        <br/>
    </div>
    <div align="center" width="200px;" height="200px;"><img src="http://www.nitethoughts.club/zan.png" width="200px;"
                                                            height="200px;"></div>
</main>
</div>
<footer id="footer" role="contentinfo" itemscope itemtype="http://schema.org/WPFooter">

    <a id="rocket" href="#top" title="返回顶部"><i></i></a>
</footer>
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