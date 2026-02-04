import CONFIG from "@/app/config";

const shouldNotLog = CONFIG.ENV === "prod" || CONFIG.MODE === "production"

export default function Log(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  if (shouldNotLog) return descriptor;  
  
  const original = descriptor.value;

    descriptor.value = function (...args: any[]) {
        const className = target.constructor.name;
        const label = `${className}.${propertyKey}`;

        const start = performance.now();

        console.groupCollapsed(`🧩 ${label}`);

        console.log("➡️ Arguments:", args);

        try {
            const result = original.apply(this, args);

            if (result instanceof Promise) {
              return result
                .then((res) => {
                  console.log("⬅️ Result:", res);
                  console.log(
                    `⏱️ Time: ${(performance.now() - start).toFixed(2)} ms`
                  );
                  console.groupEnd();
                  return res;
                })
                .catch((err) => {
                  console.error("❌ Error:", err);
                  console.groupEnd();
                  throw err;
                });
            }

            console.log("⬅️ Result:", result);
            console.log(
              `⏱️ Time: ${(performance.now() - start).toFixed(2)} ms`
            );
            console.groupEnd();
            return result;
        } catch (error) {
          console.error("❌ Error:", error);
          console.groupEnd();
          throw error;
        }
    };

    return descriptor;
}
