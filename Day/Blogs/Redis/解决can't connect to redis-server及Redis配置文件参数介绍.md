### 错误的解决方式

- 打开Redis的配置文件，`Redis.conf`。
- 有一行`bind 127.0.0.1`，修改为 `# bind 127.0.0.1`.
- 有一行`protected-mode yes`，修改为`protected-mode no`.

### `Redis。conf`相关参数介绍