message=$1

# 更新 master
git add .
git commit -m "🎨 规范 $message"
git push https://github.com/ylksty/awsjdk.git master