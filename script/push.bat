@REM Jpom���֧����Զ�˺ϲ���������

@echo off

cd ../

echo ���͵�

call git push github master

call git push gitee master

echo ����tags
call git push github --tags

call git push gitee --tags
