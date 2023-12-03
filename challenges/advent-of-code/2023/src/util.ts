import { path } from './deps.ts';

export const readInput = async (fileName: string) => {
  const __dirname = path.dirname(path.fromFileUrl(import.meta.url));
  return await Deno.readTextFile(`${__dirname}/input/${fileName}`);
}
