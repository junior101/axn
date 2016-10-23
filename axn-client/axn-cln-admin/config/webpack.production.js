/**
 * Production configuration file for webpack
 */
// Const
const ENV = process.env.NODE_ENV = process.env.ENV = 'production';
const commonConfig = require('./webpack.common.js');
// Custom plugins
const helpers = require('../plugin/helper-conf-plugin');
// Webpack Plugins
const WebpackMerge = require('webpack-merge');
const WebpackMd5Hash = require('webpack-md5-hash');
const WebpackNoErrorsPlugin = require('webpack/lib/NoErrorsPlugin');
const WebpackDedupePlugin = require('webpack/lib/optimize/DedupePlugin');
const WebpackUglifyJsPlugin = require('webpack/lib/optimize/UglifyJsPlugin');
const WebpackDefinePlugin = require('webpack/lib/DefinePlugin');
const WebpackVersionFile = require('webpack-version-file-plugin');
// Webpack configuration
module.exports = function (options) {
    return WebpackMerge(commonConfig(), {
        debug: false,
        devtool: 'cheap-module-source-map',
        output: {
            publicPath: 'axnadmpnl/', // need comment when start client in production mode on local machine
            path: helpers.root('build'),
            filename: '[name].[chunkhash].bundle.js',
            sourceMapFilename: '[name].[chunkhash].bundle.map',
            chunkFilename: '[id].[chunkhash].chunk.js'
        },
        plugins: [
            // Stops the build if there is any error
            new WebpackNoErrorsPlugin(),
            // Plugin to replace a standard webpack chunkhash with md5
            new WebpackMd5Hash(),
            // Minimise the bundles
            new WebpackUglifyJsPlugin({
                beautify: false,
                mangle: {screw_ie8: true, keep_fnames: true},
                compress: {screw_ie8: true},
                comments: false
            }),
            // Use to define environment variables that we can reference within our application
            new WebpackDefinePlugin({
                'process.env': {
                    'ENV': JSON.stringify(ENV)
                }
            }),
            // Create version file from package
            new WebpackVersionFile({
                packageFile: helpers.root('package.json'),
                template: helpers.root('version/template.ejs'),
                outputFile: helpers.root('version/result.txt')
            })
            // TODO: needed fixing https://github.com/webpack/webpack/issues/2644
            // Detects identical files and removes them from the output
            // new WebpackDedupePlugin(),
        ]
    });
};
