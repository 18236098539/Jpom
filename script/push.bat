@REM Jpom���֧����Զ�˺ϲ���������

@echo off

cd ../


echo ���͵�gitee

call git fetch github master:master

call git fetch gitee master:master

call git push gitee master

echo ���͵�github

call git push github master

echo ����tags
call git push github --tags

call git push gitee --tags
