<!DOCTYPE html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="applicable-device" content="pc,mobile">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">


<meta name="keywords" content="电影," /><meta name="description" content="" />

<title>${title}</title>
<meta name='robots' content='noindex,follow' />
<link rel='dns-prefetch' href='' />
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
		<h1 class="entry-title" itemprop="name headline" align="center">${title}</h1>
	</header>

<div class="entry-content">
<p style="padding-left: 20px;" >地址：<a href="${panurl}">${title}</a></p>
<p style="padding-left: 20px;">密码：${panpwd}</p>
    <br/>
    <br/>


    <a href="http://www.nitethoughts.club/moive/minvalid?moiveName=${encodetitle}" style="padding-left: 20px;" > 链接失效</a>
	<#if '${title}'.content?index_of("更新至")!=-1>
    <a href="http://www.nitethoughts.club/moive/murge?moiveName=${encodetitle}" style="padding-left: 60px;">  催更</a>
	</#if>

    <!--<a href="http://localhost:8081/moive/minvalid?moiveName=22" style="padding-left: 20px;" > 链接失效</a>-->
    <!--<a href="http://localhost:8081/moive/murge?moiveName=22" style="padding-left: 60px;">  催更</a>-->



    <br/>
</div>
	<div align="center" width="200px;" height="200px;"><img src="http://www.nitethoughts.club/zan.png" width="200px;" height="200px;"></div>
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