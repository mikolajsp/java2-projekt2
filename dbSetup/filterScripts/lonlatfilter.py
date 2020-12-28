import csv
import re

new_rows = []
r = csv.reader(open('schools_for_db.csv'),quotechar='"')
lines = list(r)
new_lines = []
#pattern = re.compile("^[0-9]{2}\\.[0-9]{1,}?$")
counter = 0
for line in lines:
    better = line
    if len(better) == 18:
        new_lines.append(better)
with open("new.csv", "w") as newcsv:
    writer = csv.writer(newcsv, quoting=csv.QUOTE_NONNUMERIC,delimiter=",")
    writer.writerows(new_lines)
writer = csv.writer(open('schools.csv', 'w'))
writer.writerows(lines)