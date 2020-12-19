import csv

new_rows = []
r = csv.reader(open('schools.csv'))
lines = list(r)
for line in lines[:5]:
    print(line)
new_lines = []
for line in lines:
    better = line
    better[1] = better[1].lower()[5:]
    new_lines.append(better)
print(new_lines)
with open("new.csv","w") as newcsv:
    writer = csv.writer(newcsv,quoting=csv.QUOTE_NONNUMERIC, delimiter=';')
    writer.writerows(new_lines)
# print(lines)
# writer = csv.writer(open('schools.csv', 'w'))
# writer.writerows(lines)
