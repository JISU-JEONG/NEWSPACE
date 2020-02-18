const ansiRegex = require('ansi-regex')

module.exports = {
		
	configureWebpack: {
		devtool: 'source-map'
	},
	transpileDependencies: [ansiRegex]
}