import csv
import re

new_rows = []
r = csv.reader(open('schools_for_db.csv'))
lines = list(r)
new_lines = []
pattern = re.compile("^[0-9]{2}-[0-9]{3}?$")
for line in lines:
    better = line
    if pattern.match(better[8]):
        new_lines.append(better)
with open("new.csv", "w") as newcsv:
    writer = csv.writer(newcsv, quoting=csv.QUOTE_NONNUMERIC,delimiter=",")
    writer.writerows(new_lines)
writer = csv.writer(open('schools.csv', 'w'))
writer.writerows(lines)
