@REM Jpom���֧����Զ�˺ϲ���������

@echo off

cd ../


echo ���͵�gitee


call git push gitee master

echo ���͵�github

call git push github master

echo ����tags
call git push github --tags

call git push gitee --tags
