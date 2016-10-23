/**
 * Common configuration file for webpack
 */
// Custom Plugins
const helpers = require('../plugin/helper-conf-plugin');
// Webpack Plugins
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const AssetsWebpackPlugin = require('assets-webpack-plugin');
const WebpackCommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');
const WebpackContextReplacementPlugin = require('webpack/lib/ContextReplacementPlugin');
// Webpack configuration
module.exports = function (options) {
    return {
        entry: {
            'polyfills': './src/entry/polyfills.ts',
            'vendor': './src/entry/vendor.ts',
            'main': './src/entry/main.ts'
        },
        resolve: {
            extensions: ['', '.ts', '.js', '.json'],
            modules: [helpers.root('src'), 'node_modules']
        },
        module: {
            loaders: [
                {
                    test: /\.ts$/,
                    loaders: [
                        'awesome-typescript-loader',
                        'angular2-template-loader'
                    ],
                    exclude: [/\.(spec|e2e)\.ts$/]
                },
                {
                    test: /\.json$/,
                    loader: 'json-loader'
                },
                {
                    test: /\.css$/,
                    loaders: ['to-string-loader', 'css-loader']
                },
                {
                    test: /\.html$/,
                    loader: 'raw-loader',
                    exclude: [helpers.root('src/index.html')]
                },
                {
                    test: /\.(jpg|png|gif)$/,
                    loader: 'file-loader'
                }
            ]
        },
        plugins: [
            // Copy needed files
            new CopyWebpackPlugin([
                {
                    from: helpers.root('src/assets'),
                    to: 'assets'
                },
                {
                    from: helpers.root('version/result.txt'),
                    to: 'version.txt'
                }
            ]),
            // Creation of HTML files to serve webpack bundles
            new HtmlWebpackPlugin({
                template: helpers.root('src/index.html'),
                chunksSortMode: 'dependency'
            }),
            // Create description file for assets
            new AssetsWebpackPlugin({
                path: helpers.root('build'),
                filename: 'webpack-assets.json',
                prettyPrint: true
            }),
            // Generate chunk
            new WebpackCommonsChunkPlugin({
                name: ['polyfills', 'vendor'].reverse()
            }),
            // Need for disable warning of dependency is an expression for angular core
            // https://github.com/AngularClass/angular2-webpack-starter/issues/993
            new WebpackContextReplacementPlugin(
                /angular(\\|\/)core(\\|\/)(esm(\\|\/)src|src)(\\|\/)linker/,
                helpers.root('src') // location of your src
            )
        ]
    };
};
