const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  devServer: {
    port: 3000,
    contentBase: path.resolve(__dirname, 'src/main/resources/public'),
    open: true,
    openPage: 'index.html',
    historyApiFallback: true,
  },
  mode: "development",
  entry: ["./src/main/resources/js"],
  output: {
    path: path.resolve(__dirname, 'src/main/resources/public'),
    filename: 'js/main.js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      }
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.join(__dirname, 'src', 'main', 'resources', 'html', 'index.html'),
      filename: path.join(__dirname, 'src', 'main', 'resources', 'public', 'index.html'),
    }),
  ]
};
