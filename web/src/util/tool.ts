export class Tool{
    // 空校验 null或“”都返回true
    public static isEmpty(obj: any) {
        if ( (typeof obj === 'string') ) {
            return !obj || obj.replace(/\s+/g, "") === ""
        } else {
            return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
        }
    }

    // 非空校验
    public static isNotEmpty(obj: any) {
        return !this.isEmpty(obj);
    }

    // 对象复制
    // @param obj
    public static copy(obj: object) {
        if (Tool.isNotEmpty(obj)) {
            return JSON.parse(JSON.stringify(obj));
        }
    }

    // 使用 递归 将数组转为树形结构; JavaScript
    // 父ID 属性为 parent
    // 从 array 里面拿出所有 parent=parentId的
    public static arrayToTree (array: any, parentId: number) {
        if (Tool.isEmpty(array)) {
            return [];
        }

        const result = [];
        for (let i = 0; i < array.length; i++) {
            const c = array[i];
            // console.log(Number(c.parent), Number(parentId));
            if (Number(c.parent) === Number(parentId)) {
                result.push(c);

                // check current.children using recursion
                const children = Tool.arrayToTree(array, c.id);
                if (Tool.isNotEmpty(children)) {
                    c.children = children;
                }
            }
        }
        return result;
    }
}