/**
 * Development configuration file for webpack
 */
// Const
const commonConfig = require('./webpack.common.js');
const host = process.env.HOST || 'localhost';
const port = process.env.PORT || 3000;
// Custom plugins
const helpers = require('../plugin/helper-conf-plugin');
// Webpack Plugins
const WebpackMerge = require('webpack-merge');
const WebpackNamedModulesPlugin = require('webpack/lib/NamedModulesPlugin');
// Webpack configuration
module.exports = function (options) {
    return WebpackMerge(commonConfig(), {
        debug: true,
        devtool: 'cheap-module-source-map',
        output: {
            path: helpers.root('build'),
            filename: '[name].bundle.js',
            sourceMapFilename: '[name].map',
            chunkFilename: '[id].chunk.js',
            library: 'ac_[name]',
            libraryTarget: 'var'
        },
        plugins: [
            // Uses file names as module name (experimental plugin)
            // https://github.com/webpack/webpack/commit/a04ffb928365b19feb75087c63f13cadfc08e1eb
            new WebpackNamedModulesPlugin()
        ],
        // Webpack Development Server configuration
        devServer: {
            host: host,
            port: port,
            historyApiFallback: true,
            watchOptions: {
                aggregateTimeout: 300,
                poll: 1000
            },
            outputPath: helpers.root('build')
        }
    });
};
