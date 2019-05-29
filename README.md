# 前端环境搭建 iview文件夹内问前端 技术采用iview-admin
1. 安装nodeJs
	1. 如果之前没有安装过nodeJs，可以直接从官网安装或查看第二步使用nvm管理nodeJs
	2. 通过nvm来管理nodeJs版本(nvm可以同时维护多个nodeJs版本并切换使用)
		1. 如果当前已安装nodeJs，需要卸载当前安装的nodeJs。
        需要手动删除C:\Program Files\nodejs  && C:\Users\mitol\AppData\Roaming\npm  && npm-cache
		2. 从网上寻找并安装nvm
		3. 安装成功后可以在命令行界面输入命令 nvm install v6.11.4 安装v6.11.4版本的nodeJs(更多版本请查阅官网)
			//v.x.x.x为版本号，可以同时把之前卸载的nodeJs版本也装上，支持多版本
		4. nvm use v6.11.4 //启用版本
		5. nvm list 查看已按照的nodeJs版本
2. 配置 cnpm  参考网站地址  http://blog.csdn.net/Small_Lee/article/details/68062223
	// 原生npm在使用下载组件的时候有被墙的可能，为了加快速度和稳定性我们改用cnpm
3. IDE可以使用Hbuilder 或者 idea // 只尝试过这两个
4. 第一次尝试下面推荐使用iview-admin来尝试运行检查环境是否有异常  git clone https://github.com/iview/iview-admin.git
5. 下载完成后在项目根目录下进入cmd命令行界面 运行 npm install     // 安装项目依赖，还有一种安装的方式yarn，可以百度了解 npm install -g yarn //yarn 安装依赖
6. 安装完成后 npm run dev 运行开发者模式，成功会自动打开项目页面

> 如果在安装依赖的过程中发生了任何报错，请清除缓存后再试一下 npm cache clean --force  // 清除高速缓存  npm cache verify //清除验证

# 后端环境搭建 record文件夹内为后端  技术采用 springBoot mybatis mysql
1. 使用IDEA导入项目，加载依赖即可
2. 初始化脚本在 resources\sql\mysql 路径下，依次运行即可
