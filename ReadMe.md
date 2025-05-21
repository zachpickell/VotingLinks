# 🗳️ VotingLinks

**VotingLinks** is a lightweight Minecraft plugin that listens for real-time votes via [VotifierPlus](https://github.com/BenCodez/VotifierPlus) and tracks them in a MySQL database. It’s designed to be simple, efficient, and easy to build on — perfect for developers or servers who want backend vote tracking without any GUI overhead.

---

## ✨ Features

- ✅ Listens to real player votes using VotifierPlus
- 📊 Tracks total vote counts per player in a MySQL database
- 📢 Broadcasts vote messages when players vote
- 🧾 Simple `/vote` command that shows configured voting links
- 🔧 Fully configurable via `config.yml`

---

## 📦 Installation

1. Drop the plugin JAR into your server’s `/plugins/` folder.
2. Install [VotifierPlus](https://github.com/BenCodez/VotifierPlus) and set it up.
3. Start the server to generate `config.yml`.
4. Edit `config.yml` with your MySQL database details.

---

## 🔧 Configuration (`config.yml`)

```yaml
database:
  host: "localhost"
  port: 3306
  name: "minecraft"
  user: "username"
  password: "password"

# Note

> **Important:** The voting server links are currently hardcoded in `VotingLinks.java` and must be edited there before compiling the plugin. A future update will move these links into `config.yml` for easier configuration.