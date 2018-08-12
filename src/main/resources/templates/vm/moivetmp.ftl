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
		<h1 class="entry-title" itemprop="name headline">${title}</h1>
	</header>

<div class="entry-content">
<#--<p></p>-->
<#--<p><img src="" /></p>-->
<#--<p><strong></strong><br/>-->
<#--<p>${content}</p>-->
<#--<hr />-->
<#--<p>资源</p>-->
<p>地址：<a href="${title}">${title}</a></p>
<p>密码：${panpwd}</p>
<p>&nbsp;</p>
</div>
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