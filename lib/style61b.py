import sys
import os

classpath = os.environ['CLASSPATH']

unix_dir_names = classpath.split(':')
windows_dir_names = classpath.split(';')

all_dirs = unix_dir_names
all_dirs.extend(windows_dir_names)

checkstyle_jar_name = 'checkstyle-5.9-all.jar'
checkstyle_rules_name = '61b_checks.xml'

for dirname in all_dirs:
	#strip wildcard, if any
	stripped_dirname = dirname.strip('*')
	candidate_path = os.path.join(dirname, checkstyle_jar_name)
	print(candidate_path)


#java -jar /Users/jug/work/61b/course-materials/lib/checkstyle-5.9-all.jar -c /Users/jug/work/61b/course-materials/lib/61b_checks.xml *.java