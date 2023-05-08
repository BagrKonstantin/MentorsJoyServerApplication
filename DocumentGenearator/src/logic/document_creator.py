from docxtpl import DocxTemplate
import os

main_path = ""
template_path = "src/template/ta.docx"


def generate(response):
    try:
        print('generation started')
        file_ref = open(template_path, "rb")
        template = DocxTemplate(file_ref)
        to_fill_in = {
            'teacher_name': response['teacherName'],
            'head_teacher_name': response['headTeacherName'],
            'teacher_status': response['sample']['teacher']['status'],
            'head_teacher_status': response['sample']['headTeacher']['status'],
            'year': response['sample']['year'],
            'project_name': response['sample']['programName'],

            'faculty': response['faculty'],
            'department': response['sample']['department']['title'],
            'chapter': response['chapterCode'],
            'class': response['sample']['clazz']['code'],
            'student_name': response['userName'],
            'student_status': response['sample']['user']['person']['status'],
            'project_short_name': response['sample']['programShortName'],
            'project_english_name': response['sample']['programNameEnglish'],
        }

        template.render(to_fill_in)

        filename = str(response['techAssigmentId']) + '.docx'
        filled_path = os.path.join('src/samples', filename)
        template.save(filled_path)
        file_ref.close()

        print('generation finished')
    except Exception as ex:
        print(ex)
